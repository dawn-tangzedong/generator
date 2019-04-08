package com.rockcandy.domain;

import lombok.Data;

import java.util.List;

/**
 * 表数据
 *
 * @author tangzedong.programmer@gmail.com
 * @date 2016年12月20日 上午12:02:55
 */
@Data
public class TableDO {
    /**
     * 名称
     */
    private String tableName;
    /**
     * 备注
     */
    private String comments;
    /**
     * 主键
     */
    private ColumnDO pk;
    /**
     * 表列
     */
    private List<ColumnDO> columns;

    /**
     * 表名驼峰命名(第一个字母大写)，如：sys_user => SysUser
     */
    private String className;

    /**
     * 表名驼峰命名(第一个字母小写)，如：sys_user =>sysUser
     */
    private String classname;
}
