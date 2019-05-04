package com.rockcandy.common.utils;

import java.util.HashMap;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 数据库表字段类型生成java bean类型工具包
 * @since 2019/5/4 22:37
 */
public class GeneratorTypeUtils {
    public static HashMap<String, String> Mysql = new HashMap<String, String>() {{
        put("tinyint", "Integer");
        put("smallint", "Integer");
        put("mediumint", "Integer");
        put("int", "Integer");
        put("integer", "Integer");
        put("bigint", "Long");
        put("float", "Float");
        put("double", "Double");
        put("decimal", "BigDecimal");
        put("bit", "Boolean");
        put("char", "String");
        put("varchar", "String");
        put("tinytext", "String");
        put("text", "String");
        put("mediumtext", "String");
        put("longtext", "String");
        put("json", "String");
        put("date", "Date");
        put("datetime", "Date");
        put("timestamp", "Date");
    }};

    public static HashMap<String, String> Oracle = new HashMap<String, String>() {{
        put("CHAR", "String");
        put("VARCHAR2", "String");
        put("LONG", "String");
        put("RAW", "byte[]");
        put("LONGRAW", "byte[]");
        put("DATE", "Date");
        put("TIMESTAMP", "Data");
    }};
}
