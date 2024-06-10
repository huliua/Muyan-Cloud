package com.muyan.constant;

/**
 * redis常用的常量
 *
 * @author huliua
 * @version 1.0
 * @date 2024-04-29 22:58
 */
public class RedisConstants {

    /**
     * 用户信息Key的前缀
     */
    public static final String USER_KEY_PREFIX = "user:";

    /**
     * 角色信息Key的前缀
     */
    public static final String ROLE_KEY_PREFIX = "role:";

    /**
     * 权限信息Key的前缀
     */
    public static final String PERMISSION_KEY_PREFIX = "permission:";

    /**
     * 过期时间，单位为秒
     */
    public static final int EXPIRE_TIME = 60 * 30;
}
