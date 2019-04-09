package com.rockcandy.resources;//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Date;
//
///**
// * @author tanngzedong
// * @apiNote mybatis-plus 公共字段自动填充
// * @since 2018/11/15 10:37
// */
//@Configuration
//public class CommonMetaObjectHandler implements MetaObjectHandler {
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        setFieldValByName("creationDate", new Date(), metaObject);
//        setFieldValByName("delFlag", false, metaObject);
//        setFieldValByName("status", 1, metaObject);
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        setFieldValByName("modificationDate", new Date(), metaObject);
//    }
//
//}
