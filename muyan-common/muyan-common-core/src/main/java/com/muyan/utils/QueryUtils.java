package com.muyan.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-23 21:02
 */
public class QueryUtils {

    /**
     * 将传进来的字符串数组中的没一个元素套上单引号，并以逗号分隔拼接起来返回
     * @param list ["1", "2", "3"]
     * @return "'1','2','3'"
     */
    public static String convertSqlIds(List<String> list) {
        if (CollectionUtil.isEmpty(list)) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append("'").append(s).append("'").append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
