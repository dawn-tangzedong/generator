package plo.utils.generate.core.mapper;

import org.apache.ibatis.annotations.Select;
import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;

import java.util.List;

/**
 * PostgreSQL代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-24
 */
public interface PostgreSqlGenerateMapper extends GenerateMapper {

    @Override
    @Select("SELECT t2.attname AS name, pg_type.typname AS dataType, col_description (t2.attrelid, t2.attnum) AS comment, '' AS extra, ( CASE t3.contype WHEN 'p' THEN 'PRI' ELSE '' END ) AS columnKey FROM pg_class AS t1, pg_attribute AS t2 INNER JOIN pg_type ON pg_type.oid = t2.atttypid LEFT JOIN pg_constraint t3 ON t2.attnum = t3.conkey[1] AND t2.attrelid = t3.conrelid WHERE t1.relname = #{tableName} AND t2.attrelid = t1.oid AND t2.attnum>0")
    List<ColumnDTO> queryColumns(String tableName);

    @Override
    @Select("SELECT t1.tablename AS name, obj_description (relfilenode,'pg_class') AS comment, now() AS createTime FROM pg_tables t1, pg_class t2 WHERE t1.tablename = #{tableName} AND t1.tablename = t2.relname")
    TableDTO queryTable(String tableName);
}
