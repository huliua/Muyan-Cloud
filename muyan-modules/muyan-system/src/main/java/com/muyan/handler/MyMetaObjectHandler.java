package com.muyan.handler;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义的MetaObjectHandler，用于自动填充字段
 */
@Component
public class MyMetaObjectHandler extends CustomMetaObjectHandler {

    @Override
    public void setInsertFill(MetaObject metaObject) {
        // 如果包含某个字段的setter，则赋值
        if (metaObject.hasSetter("createUser") && StpUtil.isLogin()) {
            metaObject.setValue("createUser", StpUtil.getLoginIdAsLong());
        }
        if (metaObject.hasSetter("updateUser") && StpUtil.isLogin()) {
            metaObject.setValue("updateUser", StpUtil.getLoginIdAsLong());
        }
        if (metaObject.hasSetter("createTime")) {
            metaObject.setValue("createTime", new Date());
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }
        // 默认状态为有效
        if (metaObject.hasSetter("status")) {
            metaObject.setValue("status", "1");
        }
    }

    @Override
    public void setUpdateFill(MetaObject metaObject) {
        // 如果包含某个字段的setter，则赋值
        if (metaObject.hasSetter("updateUser") && StpUtil.isLogin()) {
            metaObject.setValue("updateUser", StpUtil.getLoginIdAsLong());
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }
    }
}