package com.rockcandy.domain;

import lombok.Data;

/**
 * 列的属性
 *
 * @author tangzedong.programmer@gmail.com
 * @date 2016年12月20日 上午12:01:45
 */
@Data
public class ColumnDO {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列名类型
     */
    private String dataType;
    /**
     * 列名备注
     */
    private String comments;
    /**
     * 属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String attrName;
    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrname;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 字段长度
     */
    private Long characterOctetLength;
    /**
     * 字段列类型以及长度
     */
    private String columnType;
    /**
     * 该字段是否允许为空 YES or NO
     */
    private String isNullable;
    /**
     * auto_increment
     */
    private String extra;
}
