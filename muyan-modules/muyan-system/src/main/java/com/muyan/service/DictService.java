package com.muyan.service;

import com.muyan.domain.vo.DictVo;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:43
 */
public interface DictService {

    public List<DictVo> getDictList(String dictName, Boolean cache);
}
