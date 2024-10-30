package com.muyan.service;

import com.muyan.domain.PageResult;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.CodeShareDto;
import com.muyan.domain.dto.CodeShareInfoDto;
import com.muyan.domain.dto.CodeShareInfoPageQueryDto;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.domain.vo.CodeShareVo;

import java.io.IOException;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:19
 */
public interface CodeShareService {
    ResponseResult<String> saveCodes(CodeShareDto codeShareDto);

    ResponseResult<PageResult<CodeShareInfoVo>> getCodesList(CodeShareInfoPageQueryDto codeShareQueryDto);

    ResponseResult<String> operateCodeShareInfo(CodeShareInfoDto codeShareInfoDto);

    ResponseResult<CodeShareVo> getCodeShare(Long id);

    ResponseResult<String> deleteCodeShare(Long id);

    ResponseResult<PageResult<CodeShareInfoVo>> getCodesSearchList(CodeShareInfoPageQueryDto codeShareQueryDto) throws IOException;
}
