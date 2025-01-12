package com.muyan.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareInfoResponse {

    // 代码分享信息id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long shareCodeId;

    // 访问令牌
    private String accessToken;
}
