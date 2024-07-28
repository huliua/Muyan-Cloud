package com.muyan.controller;

import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.CodeShareDto;
import com.muyan.domain.dto.CodeShareInfoDto;
import com.muyan.domain.dto.CodeShareInfoPageQueryDto;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.service.CodeShareService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * CodeShare模块相关
 *
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:18
 */
@Slf4j
@RestController
@RequestMapping("/codeShare")
public class CodeShareController {

    @Resource
    private CodeShareService codeShareService;

    @PostMapping("/saveCodes")
    public ResponseResult<String> saveCodes(@RequestBody CodeShareDto codeShareDto) {
        return codeShareService.saveCodes(codeShareDto);
    }

    /**
     * 获取当前用户所有可见的代码列表
     */
    @PostMapping("/getCodesList")
    public ResponseResult<PageResult<CodeShareInfoVo>> getCodesList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_ALL);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 获取所有我创建的代码
     */
    @PostMapping("/getMyCodesList")
    public ResponseResult<PageResult<CodeShareInfoVo>> getMyCodesList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_MY);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 获取我收藏的所有代码列表
     */
    @PostMapping("/getMyFavourList")
    public ResponseResult<PageResult<CodeShareInfoVo>> getMyFavourList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_FAVORITE);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 收藏/取消收藏代码
     */
    @PostMapping("/favourCode")
    public ResponseResult<String> favourCode(@RequestBody CodeShareInfoDto codeShareInfoDto) {
        return codeShareService.operateCodeShareInfo(codeShareInfoDto);
    }

    /**
     * 查询代码详情
     */
    @PostMapping("/getCodeShare/{id}")
    public ResponseResult<CodeShareVo> getCodeShare(@PathVariable("id") Long id) {
        return codeShareService.getCodeShare(id);
    }
}