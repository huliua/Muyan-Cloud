package com.muyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.muyan.constant.RedisConstants;
import com.muyan.entity.DictInfo;
import com.muyan.entity.DictVo;
import com.muyan.mapper.DictInfoMapper;
import com.muyan.mapper.DictMapper;
import com.muyan.service.DictService;
import com.muyan.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:44
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictInfoMapper dictInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<DictVo> getDictList(String dictCode, Boolean cache) {
        if (StrUtil.isEmpty(dictCode)) {
            return new ArrayList<>();
        }
        if (cache) {
            // 先从redis中获取
            String strVal = (String) redisUtil.get(StrUtil.concat(true, RedisConstants.DICT_KEY_PRE, dictCode));
            if (StrUtil.isNotEmpty(strVal)) {
                return JSON.parseArray(strVal, DictVo.class);
            }
        }

        // 先根据dictName获取对应的字典表名
        DictInfo dictInfo = dictInfoMapper.selectOne(new LambdaQueryWrapper<DictInfo>().eq(DictInfo::getDictCode, dictCode));
        if (Objects.isNull(dictInfo)) {
            return new ArrayList<>();
        }

        // 存入redis,即使为空也存入，防止缓存穿透
        List<DictVo> dictList = dictMapper.getDictList(dictCode);
        redisUtil.set(StrUtil.concat(true, RedisConstants.DICT_KEY_PRE, dictCode), JSON.toJSONString(dictList), 30 * 60);
        return dictList;
    }
}
