package com.muyan.handler;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.ibatis.reflection.MetaObject;

public class MyMetaObjectHandler extends CustomMetaObjectHandler {

    @Override
    public void setInsertFill(MetaObject metaObject) {
        metaObject.setValue("createUser", StpUtil.getLoginIdAsString());
        metaObject.setValue("userId", StpUtil.getLoginIdAsString());
    }

    @Override
    public void setUpdateFill(MetaObject metaObject) {
        metaObject.setValue("updateUser", StpUtil.getLoginIdAsString());
    }
}