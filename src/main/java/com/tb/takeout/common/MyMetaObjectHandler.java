package com.tb.takeout.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createUser",BaseContext.getId());
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getId());
        metaObject.setValue("updateTime",LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateUser",BaseContext.getId());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}
