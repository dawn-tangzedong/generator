package com.rockcandy.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 通用参数配置文件，默认值从 propertiesConfig.yml 文件中获取
 * @since 2019/4/1 19:45
 */
@Data
@Component
@PropertySource(value = "classpath:application-config.yml")
@ConfigurationProperties(prefix = "properties.config")
public class PropertiesConfig {
    /**
     * 默认的main path 为 org.generator.modules
     */
    private String mainPath = "org.generator.modules";
    private String packagePath;
    private String moduleName;
    private String author;
    private String email;
    private String tablePrefix;
    private String columnPrefix;
    private String fileOutputPath;
    /**
     * 服务层基类包路径
     */
    private String baseServicePackage;
    /**
     * 推荐使用resources下{@link BaseEntity}基类，包含id,creationTime,modificationTime,delFlag
     */
    private String baseEntityPackage;
    /**
     * 使用基类忽略的属性，可以自定义
     */
    private String ignoreAttribute;
    /**
     * 是否使用swagger注解，默认为true，会在Domain以及Controller层加上swagger相关注解
     */
    private boolean useSwagger = true;
    /**
     * 是否使用shiro安全校验注解，默认为false，会在controller层上加上shiro相关注解
     */
    private boolean useShiro;
    private String generatorTableName;
    /**
     * 数据库 可选值 Mysql，Oracle，默认为Mysql
     */
    private String sqlType = "Mysql";

}
