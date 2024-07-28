package com.muyan.domain.vo;

import com.muyan.domain.entity.CodeShareFile;
import com.muyan.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-27 15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeShareVo implements Serializable {

    private static final long SerialVersionUID = 1L;

    private CodeShareInfoVo codeShareInfoVo;

    private List<CodeShareFile> codeShareFileList;

    private List<Tag> tagList;
}
