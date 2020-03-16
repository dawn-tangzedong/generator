package plo.utils.generate.enums;

import lombok.Getter;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成模块枚举
 * @since 2020-03-05 18:02
 */
@Getter
public enum GenerateModuleEnums {
    /**
     * 实体类
     */
    Domain("domain.java.ftl"),
    /**
     * 工厂类
     */
    ServiceFactory("serviceFactory.java.ftl"),
    /**
     * 控制层
     */
    Controller("controller.java.ftl"),
    /**
     * 服务层
     */
    Service("service.java.ftl"),
    /**
     * 持久层
     */
    Mapper("mapper.java.ftl"),
    /**
     * 持久层对应的xml
     */
    Xml("mapper.xml.ftl");


    private String path;

    GenerateModuleEnums(String path) {
        this.path = path;
    }
}
