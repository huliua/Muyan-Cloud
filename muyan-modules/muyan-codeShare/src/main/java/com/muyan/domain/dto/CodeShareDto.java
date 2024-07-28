package com.muyan.domain.dto;

import com.muyan.domain.entity.CodeShareFile;
import com.muyan.domain.entity.CodeShareInfo;
import com.muyan.domain.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:35
 */
@Data
public class CodeShareDto implements Serializable {

    private final Long SerialVersionUID = 1L;

    private CodeShareInfo codeShareInfo;

    private List<CodeShareFile> codeShareFileList;

    private List<Tag> tagList;
}
