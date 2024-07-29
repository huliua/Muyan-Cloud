package com.muyan.handler;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自定义的MetaObjectHandler，用于自动填充字段
 */
@Component
public class MyMetaObjectHandler extends CustomMetaObjectHandler {

    @Override
    public void setInsertFill(MetaObject metaObject) {
        // 如果包含某个字段的getter，则自动赋值
        if (metaObject.hasSetter("createUser")) {
            metaObject.setValue("createUser", StpUtil.getLoginIdAsLong());
        }
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", StpUtil.getLoginIdAsLong());
        }
        if (metaObject.hasSetter("userId")) {
            metaObject.setValue("userId", StpUtil.getLoginIdAsLong());
        }
    }

    @Override
    public void setUpdateFill(MetaObject metaObject) {
        // 如果包含某个字段的getter，则自动赋值
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", StpUtil.getLoginIdAsLong());
        }
    }
}