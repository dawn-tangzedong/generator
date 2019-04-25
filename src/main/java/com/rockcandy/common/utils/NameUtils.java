package com.rockcandy.common.utils;

import org.apache.commons.lang.WordUtils;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 名称工具类
 * @since 2019/4/11 15:17
 */
public class NameUtils {

    /**
     * 数据库表名/列名转换成java类名/属性名，并循环过滤掉 prefix
     *
     * @param name     表名
     * @param prefixes 表名需要过滤的字符串
     * @return 转换后的结果
     */
    public static String convertName(String name, String[] prefixes) {
        if (prefixes.length > 0) {
            // 循环遍历，过滤掉 prefix
            for (String prefix : prefixes) {
                name = name.replace(prefix, "");
            }
        }
        return camelCase(name);
    }



    /**
     * 驼峰命令
     */
    public static String camelCase(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }
}
