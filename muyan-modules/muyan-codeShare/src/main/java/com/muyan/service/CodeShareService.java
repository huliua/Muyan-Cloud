package com.muyan.service;

import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.CodeShareBaseInfoDto;
import com.muyan.domain.dto.CodeShareInfoDto;
import com.muyan.domain.dto.CodeShareInfoPageQueryDto;
import com.muyan.domain.dto.ShareInfoResponse;
import com.muyan.domain.entity.CodeShareFile;
import com.muyan.domain.entity.CodeShareTemplate;
import com.muyan.domain.entity.Share;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.domain.vo.ShareExtVo;
import com.muyan.domain.vo.ShareVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:19
 */
public interface CodeShareService {
    ResponseResult<String> saveBaseInfo(CodeShareBaseInfoDto codeShareBaseInfoDto);

    ResponseResult<PageResult<CodeShareInfoVo>> getCodesList(CodeShareInfoPageQueryDto codeShareQueryDto);

    ResponseResult<String> operateCodeShareInfo(CodeShareInfoDto codeShareInfoDto);

    ResponseResult<CodeShareVo> getCodeShare(Long id, String accessToken);

    ResponseResult<String> deleteCodeShare(Long id);

    ResponseResult<PageResult<CodeShareInfoVo>> getCodesSearchList(CodeShareInfoPageQueryDto codeShareQueryDto) throws IOException;

    ResponseResult<ShareVo> createShare(Share share);

    ResponseResult<ShareInfoResponse> getShareCode(Long shareId, String password);

    ResponseResult<ShareExtVo> getShareInfo(Long shareId);

    ResponseResult<String> saveCodes(List<CodeShareFile> codeShareFileList);

    ResponseResult<String> saveTemplates(List<CodeShareTemplate> codeShareTemplateList);

    ResponseResult<List<CodeShareTemplate>> getTemplateFields(Long id);

    ResponseResult<List<CodeShareFile>> genCode(Long id, Map<String, Object> templateFieldMap);
}
