package com.muyan.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 当不存在自定义的MetaObjectHandler时，创建通用的MetaObjectHandler
 *
 * @author huliua
 * @version 1.0
 * @date 2024-07-23 21:11
 */
@ConditionalOnMissingBean(MetaObjectHandler.class)
@Component
@Order
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
        setInsertFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", new Date());
        setUpdateFill(metaObject);
    }

    public void setInsertFill(MetaObject metaObject) {
    }

    public void setUpdateFill(MetaObject metaObject) {
    }
}
