package com.muyan.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muyan.domain.entity.CodeShareFile;
import com.muyan.domain.entity.Tag;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-09-03 22:01
 */
@Data
@Document(indexName = "code-share", writeTypeHint = WriteTypeHint.FALSE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
@JsonDeserialize
public class CodeShareSearchDto {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String description;

    private String visibility;

    private String password;

    private String cover;

    private Integer hasStared;

    @Field(type = FieldType.Keyword)
    private Long userId;

    @Field(type = FieldType.Nested, includeInParent = true, analyzer = "ik_smart")
    private List<CodeShareFile> codeShareFileList;

    @Field(type= FieldType.Nested, includeInParent = true, analyzer = "ik_smart")
    private List<Tag> tagList;
}
