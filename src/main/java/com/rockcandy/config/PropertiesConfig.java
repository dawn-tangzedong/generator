package com.rockcandy.config;

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
@ConfigurationProperties(prefix = "properties.config")
@PropertySource("classpath:propertiesConfig.yml")
public class PropertiesConfig {
    private String mainPath;
    private String packagePath;
    private String moduleName;
    private String author;
    private String email;
    private String tablePrefix;
    private String columnPrefix;

    public PropertiesConfig() {
    }

    public PropertiesConfig(String mainPath, String packagePath, String moduleName, String author, String email, String tablePrefix, String columnPrefix) {
        this.mainPath = mainPath;
        this.packagePath = packagePath;
        this.moduleName = moduleName;
        this.author = author;
        this.email = email;
        this.tablePrefix = tablePrefix;
        this.columnPrefix = columnPrefix;
    }
}
