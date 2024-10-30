package com.muyan.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.CodeShareDto;
import com.muyan.domain.dto.CodeShareInfoDto;
import com.muyan.domain.dto.CodeShareInfoPageQueryDto;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.service.CodeShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
@Tag(name = "代码分享模块")
public class CodeShareController {

    @Resource
    private CodeShareService codeShareService;

    @PostMapping("/saveCodes")
    @Operation(summary = "保存代码")
    public ResponseResult<String> saveCodes(@RequestBody CodeShareDto codeShareDto) {
        return codeShareService.saveCodes(codeShareDto);
    }

    /**
     * 获取当前用户所有可见的代码列表
     */
    @PostMapping("/getCodesList")
    @Operation(summary = "获取代码列表")
    public ResponseResult<PageResult<CodeShareInfoVo>> getCodesList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_ALL);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 获取所有我创建的代码
     */
    @PostMapping("/getMyCodesList")
    @Operation(summary = "获取我创建的代码列表")
    public ResponseResult<PageResult<CodeShareInfoVo>> getMyCodesList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_MY);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 获取我收藏的所有代码列表
     */
    @PostMapping("/getMyFavourList")
    @Operation(summary = "获取我收藏的代码列表")
    public ResponseResult<PageResult<CodeShareInfoVo>> getMyFavourList(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) {
        codeShareQueryDto.setQueryType(CodeShareConstants.QUERY_TYPE_FAVORITE);
        return codeShareService.getCodesList(codeShareQueryDto);
    }

    /**
     * 收藏/取消收藏代码
     */
    @PostMapping("/favourCode")
    @Operation(summary = "收藏/取消收藏代码")
    public ResponseResult<String> favourCode(@RequestBody CodeShareInfoDto codeShareInfoDto) {
        return codeShareService.operateCodeShareInfo(codeShareInfoDto);
    }

    /**
     * 查询代码详情
     */
    @PostMapping("/getCodeShare/{id}")
    @Operation(summary = "查询代码详情")
    public ResponseResult<CodeShareVo> getCodeShare(@PathVariable("id") Long id) {
        return codeShareService.getCodeShare(id);
    }

    @PostMapping("/deleteCodeShare")
    @Operation(summary = "删除代码")
    public ResponseResult<String> deleteCodeShare(@PathParam("id") Long id) {
        return codeShareService.deleteCodeShare(id);
    }

    @PostMapping("/search")
    public ResponseResult<PageResult<CodeShareInfoVo>> searchCodeShare(@RequestBody CodeShareInfoPageQueryDto codeShareQueryDto) throws IOException {
        return codeShareService.getCodesSearchList(codeShareQueryDto);
    }

    @GetMapping("/getToken")
    public ResponseResult<String> getToken() {
        // 执行登录
        StpUtil.login(1L);
        // 获取token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return ResponseResult.success(tokenInfo.getTokenValue());
    }
}