package com.muyan.domain.dto;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpireEnum implements IEnum<Integer> {

    NoLimit(-1, "永久"),

    OneDay(1, "一天"),

    OneWeek(7, "一周"),

    OneMonth(30, "一个月"),

    ThreeMonth(90, "三个月"),

    HalfYear(180, "半年"),

    OneYear(365, "一年");

    private final Integer code;

    private final String message;

    @Override
    public Integer getValue() {
        return this.code;
    }
}
