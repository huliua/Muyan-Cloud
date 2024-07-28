package com.muyan.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-23 21:11
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        setInsertFill(metaObject);
    }

    public void setInsertFill(MetaObject metaObject) {
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        setUpdateFill(metaObject);
    }

    public void setUpdateFill(MetaObject metaObject) {
    }
}
