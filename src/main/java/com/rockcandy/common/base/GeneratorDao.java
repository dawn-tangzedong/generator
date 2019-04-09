package com.rockcandy.common.base;

import com.rockcandy.domain.ColumnDO;
import com.rockcandy.domain.TableDO;

import java.util.List;
import java.util.Map;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Code Generator Data Access Object
 * @since 2019/4/1 14:35
 */
public interface GeneratorDao {
    /**
     * 查询数据库表数据集合
     *
     * @param map the params
     * @return the table list
     */
    List<TableDO> queryList(Map<String, Object> map);

    /**
     * 查询数据库表数据集合总数
     *
     * @param map the params
     * @return 查询结果总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 查询数据库表结构信息
     *
     * @param tableName 过滤条件：表名
     * @return 表结构信息
     */
    TableDO queryTable(String tableName);

    /**
     * 查询数据库表列信息
     *
     * @param tableName 过滤条件：表名
     * @return 列信息集合
     */
    List<ColumnDO> queryColumns(String tableName);
}
