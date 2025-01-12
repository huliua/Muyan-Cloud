package com.muyan.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muyan.constant.RedisConstants;
import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.*;
import com.muyan.domain.entity.*;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.domain.vo.ShareExtVo;
import com.muyan.domain.vo.ShareVo;
import com.muyan.exception.ForbiddenException;
import com.muyan.mapper.*;
import com.muyan.service.CodeShareService;
import com.muyan.utils.EncodeUtils;
import com.muyan.utils.QueryUtils;
import com.muyan.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:19
 */
@Service
@Slf4j
public class CodeShareServiceImpl implements CodeShareService {

    @Resource
    private CodeShareInfoMapper codeShareInfoMapper;
    @Resource
    private CodeShareFileMapper codeShareFileMapper;
    @Resource
    private CodeShareTagMapper codeShareTagMapper;

    @Resource
    private TagMapper tagMapper;
    @Resource
    private CodeShareFavoriteMapper codeShareFavoriteMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ShareMapper shareMapper;
    @Resource
    private EncodeUtils encodeUtils;


    @Override
    @Transactional
    public ResponseResult<String> saveCodes(CodeShareDto codeShareDto) {
        // 保存代码分享信息
        if (codeShareDto.getCodeShareInfo().getId() == null) {
            codeShareInfoMapper.insert(codeShareDto.getCodeShareInfo());
        } else {
            // 判断是否有权限修改
            Long count = codeShareInfoMapper.selectCount(new LambdaQueryWrapper<CodeShareInfo>().eq(CodeShareInfo::getId, codeShareDto.getCodeShareInfo().getId()).eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
            if (null == count || count == 0L) {
                return ResponseResult.fail(403, "您没有权限操作该数据");
            }

            codeShareInfoMapper.updateById(codeShareDto.getCodeShareInfo());

            // 删除文件信息
            LambdaQueryWrapper<CodeShareFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CodeShareFile::getInfoId, codeShareDto.getCodeShareInfo().getId());
            codeShareFileMapper.delete(queryWrapper);

            // 删除标签信息
            LambdaQueryWrapper<CodeShareTag> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(CodeShareTag::getInfoId, codeShareDto.getCodeShareInfo().getId());
            codeShareTagMapper.delete(queryWrapper2);
        }

        // 更新文件信息的infoId
        codeShareDto.getCodeShareFileList().forEach(codeShareFile -> codeShareFile.setInfoId(codeShareDto.getCodeShareInfo().getId()));
        // 插入文件信息
        if (CollectionUtil.isNotEmpty(codeShareDto.getCodeShareFileList())) {
            codeShareFileMapper.insertBatchSomeColumn(codeShareDto.getCodeShareFileList());
        }

        // 更新文件信息的tag
        List<CodeShareTag> codeShareTagList = new ArrayList<>();
        if (codeShareDto.getTagList() != null) {
            codeShareDto.getTagList().forEach(tag -> {
                String tagCode = tag.getCode();
                if (StrUtil.isEmpty(tagCode)) {
                    // 生成tagCode
                    tagCode = IdUtil.fastUUID();
                    tag.setCode(tagCode);
                }
                // 保存便签信息
                CodeShareTag codeShareTag = new CodeShareTag();
                codeShareTag.setInfoId(codeShareDto.getCodeShareInfo().getId());
                codeShareTag.setTagCode(tagCode);
                codeShareTagList.add(codeShareTag);
            });
        }
        if (CollectionUtil.isNotEmpty(codeShareTagList)) {
            codeShareTagMapper.insertBatchSomeColumn(codeShareTagList);
        }

        // 新增tag数据
        if (CollectionUtil.isNotEmpty(codeShareDto.getTagList())) {
            tagMapper.insertNotExists(codeShareDto.getTagList());
            // 删除redis中的缓存
            redisUtil.del(StrUtil.concat(true, RedisConstants.DICT_KEY_PRE, "t_tag"));
            // 创建延迟任务队列
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            // 提交延迟删除任务
            executor.schedule(() -> {
                log.info("开始延迟双删...");
                redisUtil.del(StrUtil.concat(true, RedisConstants.DICT_KEY_PRE, "t_tag"));
            }, 2000, TimeUnit.MILLISECONDS);
        }

        // 返回当前id
        return ResponseResult.success(String.valueOf(codeShareDto.getCodeShareInfo().getId()));
    }

    @Override
    public ResponseResult<PageResult<CodeShareInfoVo>> getCodesList(CodeShareInfoPageQueryDto codeShareQueryDto) {
        PageResult<CodeShareInfoVo> result = new PageResult<>();
        // 构建查询条件
        Long userId = StpUtil.getLoginIdAsLong();
        Page<CodeShareInfo> infoPage = new Page<>(codeShareQueryDto.getPageNum(), codeShareQueryDto.getPageSize());
        LambdaQueryWrapper<CodeShareInfo> queryWrapper = new LambdaQueryWrapper<>();
        // 根据查询类型,拼接数据条件
        switch (codeShareQueryDto.getQueryType()) {
            case CodeShareConstants.QUERY_TYPE_FAVORITE ->
                    queryWrapper.exists("select 1 from t_code_share_favorite t where t.codeInfoId=t_code_share_info.id and t.userId={0}", userId);
            case CodeShareConstants.QUERY_TYPE_MY -> queryWrapper.eq(CodeShareInfo::getUserId, userId);
            default -> {
                queryWrapper.and(wrapper -> wrapper.eq(CodeShareInfo::getVisibility, "public").or().eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
            }
        }
        // 构建查询条件
        queryWrapper.exists(CollectionUtil.isNotEmpty(codeShareQueryDto.getTag()), "select 1 from t_code_share_tag t where t.infoId=t_code_share_info.id and t.tagCode in(" + QueryUtils.convertSqlIds(codeShareQueryDto.getTag()) + ")");
        if (CollectionUtil.isNotEmpty(codeShareQueryDto.getCreateTime())) {
            queryWrapper.between(CodeShareInfo::getCreateTime, codeShareQueryDto.getCreateTime().get(0), codeShareQueryDto.getCreateTime().get(1));
        }
        queryWrapper.like(StrUtil.isNotEmpty(codeShareQueryDto.getTitle()), CodeShareInfo::getTitle, codeShareQueryDto.getTitle());
        queryWrapper.like(StrUtil.isNotEmpty(codeShareQueryDto.getDescription()), CodeShareInfo::getDescription, codeShareQueryDto.getDescription());
        queryWrapper.orderByDesc(CodeShareInfo::getCreateTime);

        // 分页查询codeShareInfo信息
        Page<CodeShareInfoVo> codeShareInfoPage = codeShareInfoMapper.getCodesListByUserId(infoPage, queryWrapper, userId);

        // 封装vo
        List<CodeShareInfoVo> codeShareInfoVoList = codeShareInfoPage.getRecords();
        // 构建返回结果
        result.setRows(codeShareInfoVoList);
        result.setTotal(codeShareInfoPage.getTotal());
        result.setPageSize(codeShareInfoPage.getSize());
        result.setPageNum(codeShareInfoPage.getCurrent());
        return ResponseResult.success(result);
    }

    @Override
    @Transactional
    public ResponseResult<String> operateCodeShareInfo(CodeShareInfoDto codeShareInfoDto) {
        // 根据不同的操作类型,执行不同的方法
        switch (codeShareInfoDto.getOperateType()) {
            case CodeShareConstants.OPERATE_TYPE_FAVORITE -> {
                operateFavorite(codeShareInfoDto.getId(), true);
            }
            case CodeShareConstants.OPERATE_TYPE_UNDO_FAVORITE -> {
                operateFavorite(codeShareInfoDto.getId(), false);
            }
            default -> {
                return ResponseResult.fail("不支持的操作类型!");
            }
        }
        return ResponseResult.success("操作成功!");
    }

    @Override
    public ResponseResult<CodeShareVo> getCodeShare(Long id, String accessToken) {
        // 先获取代码信息
        LambdaQueryWrapper<CodeShareInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeShareInfo::getId, id);

        // 如果是根据accessToken访问，则需要判断redis中是否存在该token
        if (StrUtil.isNotEmpty(accessToken)) {
            if (!redisUtil.hasKey(RedisConstants.CODE_SHARE_ACCESS_TOKEN_KEY_PREFIX + id + ":" + StpUtil.getLoginIdAsString())) {
                return ResponseResult.fail("访问信息已过期!");
            }
        } else {
            // 否则就只能查询当前登录用户可见的数据
            queryWrapper.and(wrapper -> wrapper.eq(CodeShareInfo::getVisibility, "public").or().eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
        }

        CodeShareInfo codeShareInfo = codeShareInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(codeShareInfo)) {
            return ResponseResult.fail("未查询到代码信息,或没有查询权限!");
        }

        // 获取文件信息
        List<CodeShareFile> codeShareFileList = codeShareFileMapper.selectList(new LambdaQueryWrapper<CodeShareFile>().eq(CodeShareFile::getInfoId, id));
        // 获取标签信息
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>().exists("select 1 from t_code_share_tag t where t.infoId={0} and t.tagCode=t_tag.code", id));

        CodeShareVo codeShareVo = new CodeShareVo();
        codeShareVo.setCodeShareInfoVo(BeanUtil.copyProperties(codeShareInfo, CodeShareInfoVo.class));
        codeShareVo.setCodeShareFileList(codeShareFileList);
        codeShareVo.setTagList(tagList);

        return ResponseResult.success(codeShareVo);
    }

    @Override
    public ResponseResult<String> deleteCodeShare(Long id) {
        // 先删除代码信息(有权限控制)
        LambdaQueryWrapper<CodeShareInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeShareInfo::getId, id);
        queryWrapper.and(wrapper -> wrapper.eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
        int count = codeShareInfoMapper.delete(queryWrapper);
        if (count == 0) {
            return ResponseResult.fail("删除失败！");
        }
        // 删除代码文件信息
        codeShareFileMapper.delete(new LambdaQueryWrapper<CodeShareFile>().eq(CodeShareFile::getInfoId, id));
        // 删除标签信息
        codeShareTagMapper.delete(new LambdaQueryWrapper<CodeShareTag>().eq(CodeShareTag::getInfoId, id));
        // 删除收藏信息
        codeShareFavoriteMapper.delete(new LambdaQueryWrapper<CodeShareFavorite>().eq(CodeShareFavorite::getCodeInfoId, id));
        // 删除分享信息
        shareMapper.delete(new LambdaQueryWrapper<Share>().eq(Share::getCodeId, id));
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageResult<CodeShareInfoVo>> getCodesSearchList(CodeShareInfoPageQueryDto codeShareQueryDto) throws IOException {
        PageResult<CodeShareInfoVo> result = new PageResult<>();
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult<ShareVo> createShare(Share share) {
        // 参数校验
        if (Objects.isNull(share.getCodeId()) || Objects.isNull(share.getExpire())) {
            return ResponseResult.fail("参数错误!");
        }
        // 处理密码
        if (StrUtil.isNotEmpty(share.getPassword())) {
            share.setPassword(BCrypt.hashpw(share.getPassword(), BCrypt.gensalt()));
        }
        // 计算出过期时间,永久时间为null
        if (share.getExpire() != ExpireEnum.NoLimit) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, share.getExpire().getCode());
            share.setExpireTime(instance.getTime());
        }
        // 先删除当前代码的其他分享信息
        shareMapper.delete(new LambdaQueryWrapper<Share>().eq(Share::getCodeId, share.getCodeId()));

        // 插入新的分享信息
        shareMapper.insert(share);

        // 封装返回结果
        ShareVo shareVo = new ShareVo();
        shareVo.setShareId(share.getId());
        shareVo.setExpireTime(share.getExpireTime());
        return ResponseResult.success(shareVo);
    }

    @Override
    public ResponseResult<ShareExtVo> getShareInfo(Long shareId) {
        Share share = shareMapper.selectById(shareId);
        ShareExtVo shareExtVo = new ShareExtVo();
        if (Objects.isNull(share)) {
            return ResponseResult.fail("分享信息不存在！");
        }
        shareExtVo.setIsExpire(share.getExpireTime() != null && share.getExpireTime().before(new Date()));
        shareExtVo.setNeedPassword(StrUtil.isNotEmpty(share.getPassword()));
        return ResponseResult.success(shareExtVo);
    }

    @Override
    public ResponseResult<ShareInfoResponse> getShareCode(Long shareId, String password) {
        // 获取分享信息
        Share share = shareMapper.selectById(shareId);
        if (Objects.isNull(share)) {
            return ResponseResult.fail("分享信息不存在!");
        }
        if (share.getExpireTime() != null && share.getExpireTime().before(new Date())) {
            return ResponseResult.fail("分享已过期!");
        }
        if (share.getPassword() != null) {
            if (StrUtil.isEmpty(password)) {
                return ResponseResult.fail("请输入密码!");
            }
            if (!BCrypt.checkpw(password, share.getPassword())) {
                return ResponseResult.fail("密码错误!");
            }
        }
        // 生成token
        String accessToken = StrUtil.uuid();
        ShareInfoResponse shareInfoResponse = new ShareInfoResponse(share.getCodeId(), accessToken);

        // 将token存入redis中(key过期时间计算方式: 默认30分钟, 如果分享信息有过期时间,则取两者中较小的作为过期时间)
        long tokenExpireTime = RedisConstants.EXPIRE_TIME;
        if (share.getExpireTime() != null) {
            long expireTime = (share.getExpireTime().getTime() - System.currentTimeMillis()) / 1000;
            // 获取其中较小的作为过期时间
            tokenExpireTime = Math.min(tokenExpireTime, expireTime);
        }
        redisUtil.set(RedisConstants.CODE_SHARE_ACCESS_TOKEN_KEY_PREFIX + share.getCodeId() + ":" + StpUtil.getLoginIdAsString(), "1", tokenExpireTime);

        return ResponseResult.success(shareInfoResponse);
    }

    private void operateFavorite(Long codeInfoId, boolean isFavorite) {
        Long userId = StpUtil.getLoginIdAsLong();
        if (isFavorite) {
            // 判断收藏的数据是否有查看权限
            LambdaQueryWrapper<CodeShareInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CodeShareInfo::getVisibility, "public").or().eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
            lambdaQueryWrapper.eq(CodeShareInfo::getId, codeInfoId);
            if (codeShareInfoMapper.selectCount(lambdaQueryWrapper) <= 0) {
                throw new ForbiddenException("您没有权限收藏该代码!");
            }

            // 添加收藏
            CodeShareFavorite codeShareFavorite = new CodeShareFavorite();
            codeShareFavorite.setCodeInfoId(codeInfoId);
            codeShareFavorite.setUserId(userId);
            codeShareFavoriteMapper.insert(codeShareFavorite);
        } else {
            // 取消收藏
            LambdaQueryWrapper<CodeShareFavorite> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CodeShareFavorite::getCodeInfoId, codeInfoId).eq(CodeShareFavorite::getUserId, userId);
            codeShareFavoriteMapper.delete(queryWrapper);
        }

    }
}
