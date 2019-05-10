package com.rockcandy.common.config;

import com.rockcandy.common.constants.TypeConstants;
import com.rockcandy.common.vo.BaseClassConfigVo;
import com.rockcandy.common.vo.UsualFrameConfigVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 通用参数配置文件，默认值从 propertiesConfig.yml 文件中获取
 * @since 2019/4/1 19:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "properties.config")
public class PropertiesConfig implements Serializable {
    private String mainPath = "com.generator.common";
    private String packagePath = "com.generator.main.modules";
    private String moduleName = "demo";
    private String author = "anonymity";
    /**
     * service层基类配置信息
     */
    private BaseClassConfigVo baseServiceConfig = new BaseClassConfigVo();
    /**
     * controller层返回视图对象配置信息
     */
    private BaseClassConfigVo ctrlResultConfig = new BaseClassConfigVo();
    /**
     * domain层基类配置信息
     */
    private BaseClassConfigVo baseEntityConfig = new BaseClassConfigVo();
    /**
     * 常用框架配置信息
     */
    private UsualFrameConfigVo usualFrameConfig = new UsualFrameConfigVo();
    /**
     * 表前缀过滤
     */
    private String tablePrefix;
    /**
     * 列前缀过滤
     */
    private String columnPrefix;
    /**
     * 文件输出路径
     */
    private String fileOutputPath;
    /**
     * 使用基类忽略的属性，可以自定义
     */
    private String[] ignoreAttributes;
    /**
     * 需要生成的表名
     */
    private String[] generatorTableName;
    /**
     * 数据库 可选值 Mysql，Oracle，默认为Mysql
     */
    private String sqlType = TypeConstants.Mysql;

    @Override
    public String toString() {
        return "PropertiesConfig{" +
                "mainPath='" + mainPath + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", author='" + author + '\'' +
                ", baseServiceConfig=" + baseServiceConfig +
                ", ctrlResultConfig=" + ctrlResultConfig +
                ", baseEntityConfig=" + baseEntityConfig +
                ", usualFrameConfig=" + usualFrameConfig +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", columnPrefix='" + columnPrefix + '\'' +
                ", fileOutputPath='" + fileOutputPath + '\'' +
                ", ignoreAttributes=" + Arrays.toString(ignoreAttributes) +
                ", generatorTableName=" + Arrays.toString(generatorTableName) +
                ", sqlType='" + sqlType + '\'' +
                '}';
    }
}
