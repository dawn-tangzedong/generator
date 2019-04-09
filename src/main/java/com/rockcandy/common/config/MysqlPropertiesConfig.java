package com.rockcandy.common.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote mysql数据类型与java数据类型映射关系
 * @since 2019/4/8 21:34
 */
public class MysqlPropertiesConfig extends PropertiesConfig {
    private Map<String, String> dataType = new HashMap<>(1 << 5);

    /**
     * 初始化默认配置
     */
    public MysqlPropertiesConfig() {
        dataType.put("tinyint", "Integer");
        dataType.put("smallint", "Integer");
        dataType.put("mediumint", "Integer");
        dataType.put("int", "Integer");
        dataType.put("integer", "Integer");
        dataType.put("bigint", "Long");
        dataType.put("float", "Float");
        dataType.put("double", "Double");
        dataType.put("decimal", "BigDecimal");
        dataType.put("bit", "Boolean");
        dataType.put("char", "String");
        dataType.put("varchar", "String");
        dataType.put("tinytext", "String");
        dataType.put("text", "String");
        dataType.put("mediumtext", "String");
        dataType.put("longtext", "String");
        dataType.put("json", "String");
        dataType.put("date", "Date");
        dataType.put("datetime", "Date");
        dataType.put("timestamp", "Date");
    }

    public MysqlPropertiesConfig(Map<String, String> dataType) {
        this.dataType = dataType;
    }
}
