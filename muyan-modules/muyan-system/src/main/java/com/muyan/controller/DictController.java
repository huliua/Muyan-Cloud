package com.muyan.controller;

import com.muyan.domain.ResponseResult;
import com.muyan.entity.DictVo;
import com.muyan.service.DictService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:16
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Resource
    private DictService dictService;

    @RequestMapping("/getDictByDictCode")
    public ResponseResult<List<DictVo>> getDictList(String dictCode, Boolean cache) {
        if (Objects.isNull(cache)) {
            cache = true;
        }
        List<DictVo> dictList = dictService.getDictList(dictCode, cache);
        return ResponseResult.success(dictList);
    }


}
