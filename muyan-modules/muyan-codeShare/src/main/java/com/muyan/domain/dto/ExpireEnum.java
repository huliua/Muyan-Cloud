package com.muyan.domain.dto;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpireEnum {

    NoLimit(-1, "永久"),

    OneDay(1, "1天"),

    OneWeek(7, "7天"),

    OneMonth(30, "30天"),

    HalfYear(180, "半年"),

    OneYear(365, "1年");

    @EnumValue
    private final Integer code;

    @JsonValue
    private final String message;
}
