package com.muyan.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.*;
import com.muyan.domain.entity.CodeShareFile;
import com.muyan.domain.entity.CodeShareTemplate;
import com.muyan.domain.entity.Share;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.domain.vo.ShareExtVo;
import com.muyan.domain.vo.ShareVo;
import com.muyan.service.CodeShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * CodeShare模块相关
 *
 * @author huliua
 * @version 1.0
 * @since 2024-06-12 20:18
 */
@Slf4j
@RestController
@RequestMapping("/codeShare")
@Tag(name = "代码分享模块")
public class CodeShareController {

    @Resource
    private CodeShareService codeShareService;

    @PostMapping("/saveBaseInfo")
    @Operation(summary = "保存基础信息")
    public ResponseResult<String> saveBaseInfo(@RequestBody CodeShareBaseInfoDto codeShareBaseInfoDto) {
        return codeShareService.saveBaseInfo(codeShareBaseInfoDto);
    }

    @PostMapping("/saveCodes")
    @Operation(summary = "保存代码")
    public ResponseResult<String> saveCodes(@RequestBody List<CodeShareFile> codeShareFileList) {
        return codeShareService.saveCodes(codeShareFileList);
    }

    @PostMapping("/saveTemplates")
    @Operation(summary = "保存代码模板")
    public ResponseResult<String> saveTemplates(@RequestBody List<CodeShareTemplate> codeShareTemplateList) {
        return codeShareService.saveTemplates(codeShareTemplateList);
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
    public ResponseResult<CodeShareVo> getCodeShare(@PathVariable("id") Long id, @RequestBody AccessTokenRequest accessToken) {
        return codeShareService.getCodeShare(id, accessToken.getAccessToken());
    }

    @PostMapping("/deleteCodeShare/{id}")
    @Operation(summary = "删除代码")
    public ResponseResult<String> deleteCodeShare(@PathVariable("id") Long id) {
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

    @PostMapping("/createShare")
    public ResponseResult<ShareVo> createShare(@RequestBody Share share) {
        return codeShareService.createShare(share);
    }

    // 获取分享的信息，比如是否需要密码，是否过期
    @PostMapping("/share/{shareId}")
    public ResponseResult<ShareExtVo> getShareInfo(@PathVariable Long shareId) {
        return codeShareService.getShareInfo(shareId);
    }

    @PostMapping("/getShareCode/{shareId}")
    public ResponseResult<ShareInfoResponse> getShareCode(@PathVariable Long shareId, @RequestBody ShareInfoRequest shareInfoRequest) {
        return codeShareService.getShareCode(shareId, shareInfoRequest.getPassword());
    }

    @PostMapping("/getTemplateFields/{id}")
    public ResponseResult<List<CodeShareTemplate>> getTemplateFields(@PathVariable Long id) {
        return codeShareService.getTemplateFields(id);
    }

    @PostMapping("/genCode/{id}")
    public ResponseResult<List<CodeShareFile>> genCode(@PathVariable Long id, @RequestBody Map<String, Object> templateFieldMap) {
        return codeShareService.genCode(id, templateFieldMap);
    }

    @PostMapping("/downloadCode/{id}")
    public void downloadCode(@PathVariable Long id, @RequestBody Map<String, Object> templateFieldMap, HttpServletResponse response) {
        codeShareService.downloadCode(id, templateFieldMap, response);
    }
}