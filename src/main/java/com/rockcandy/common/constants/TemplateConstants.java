package com.rockcandy.common.constants;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 模板通用常量
 * @since 2019/5/4 22:08
 */
public interface TemplateConstants {
    String Entity = "template/Domain.java.vm";
    String Mapper = "template/Mapper.java.vm";
    String MapperXml = "template/Mapper.xml.vm";
    String Service = "template/Service.java.vm";
    String Controller = "template/Controller.java.vm";
//    String Menu = "template/menu.sql.vm";

    String[] Templates = new String[]{
            Entity,
            Mapper,
            MapperXml,
            Service,
            Controller,
//            Menu
    };


}
