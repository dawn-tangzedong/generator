package com.rockcandy.modules.oracle.dao;

import com.rockcandy.modules.common.dao.GeneratorDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Oracle相关查询持久层
 * @since 2019/3/25 16:24
 */
@Mapper
public interface OracleGeneratorDao extends GeneratorDao {

    /**
     * 根据表名查询表主键
     *
     * @param tableName 表名
     * @return 主键列名
     */
    String getPrimaryKey(String tableName);
}
