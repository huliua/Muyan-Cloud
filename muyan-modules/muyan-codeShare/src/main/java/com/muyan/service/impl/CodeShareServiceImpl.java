package com.muyan.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.CodeShareDto;
import com.muyan.domain.dto.CodeShareInfoDto;
import com.muyan.domain.dto.CodeShareInfoPageQueryDto;
import com.muyan.domain.entity.*;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.exception.ForbiddenException;
import com.muyan.mapper.*;
import com.muyan.service.CodeShareService;
import com.muyan.utils.QueryUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    @Transactional
    public ResponseResult<String> saveCodes(CodeShareDto codeShareDto) {
        // 保存代码分享信息
        if (codeShareDto.getCodeShareInfo().getId() == null) {
            codeShareInfoMapper.insert(codeShareDto.getCodeShareInfo());
        } else {
            codeShareInfoMapper.updateById(codeShareDto.getCodeShareInfo());

            // 删除文件信息
            LambdaQueryWrapper<CodeShareFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CodeShareFile::getInfoId, codeShareDto.getCodeShareInfo().getId());
            codeShareFileMapper.delete(queryWrapper);

            // 删除标签信息
            LambdaQueryWrapper<CodeShareTag> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper.eq(CodeShareFile::getInfoId, codeShareDto.getCodeShareInfo().getId());
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
                String tagCode = tag.getTagCode();
                if (StrUtil.isEmpty(tagCode)) {
                    // 生成tagCode
                    tagCode = IdUtil.fastUUID();
                    tag.setTagCode(tagCode);
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
        tagMapper.insertNotExists(codeShareDto.getTagList());

        return ResponseResult.success();
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
    public ResponseResult<CodeShareVo> getCodeShare(Long id) {
        // 先获取代码信息(有权限控制)
        LambdaQueryWrapper<CodeShareInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeShareInfo::getId, id);
        queryWrapper.and(wrapper -> wrapper.eq(CodeShareInfo::getVisibility, "public").or().eq(CodeShareInfo::getUserId, StpUtil.getLoginIdAsLong()));
        CodeShareInfo codeShareInfo = codeShareInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(codeShareInfo)) {
            return ResponseResult.fail("未查询到代码信息,或没有查询权限!");
        }

        // 获取文件信息
        List<CodeShareFile> codeShareFileList = codeShareFileMapper.selectList(new LambdaQueryWrapper<CodeShareFile>().eq(CodeShareFile::getInfoId, id));
        // 获取标签信息
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>().exists("select 1 from t_code_share_tag t where t.infoId={0} and t.tagCode=t_tag.tagCode", id));

        CodeShareVo codeShareVo = new CodeShareVo();
        codeShareVo.setCodeShareInfoVo(BeanUtil.copyProperties(codeShareInfo, CodeShareInfoVo.class));
        codeShareVo.setCodeShareFileList(codeShareFileList);
        codeShareVo.setTagList(tagList);

        return ResponseResult.success(codeShareVo);
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
