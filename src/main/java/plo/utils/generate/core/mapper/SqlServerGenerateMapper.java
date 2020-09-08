package plo.utils.generate.core.mapper;

import org.apache.ibatis.annotations.Select;
import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;

import java.util.List;

/**
 * SQLServer代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-24
 */
public interface SqlServerGenerateMapper extends GenerateMapper {

    @Override
    @Select("SELECT cast(b.NAME AS VARCHAR(500)) AS name, cast(sys.types.NAME AS VARCHAR(500)) AS dataType, cast(c.VALUE AS VARCHAR(500)) AS comment, (SELECT CASE count(1) WHEN 1 THEN 'PRI' ELSE '' END FROM syscolumns, sysobjects, sysindexes, sysindexkeys, systypes WHERE syscolumns.xusertype = systypes.xusertype AND syscolumns.id = object_id(A.NAME) AND sysobjects.xtype = 'PK' AND sysobjects.parent_obj = syscolumns.id AND sysindexes.id = syscolumns.id AND sysobjects.NAME = sysindexes.NAME AND sysindexkeys.id = syscolumns.id AND sysindexkeys.indid = sysindexes.indid AND syscolumns.colid = sysindexkeys.colid AND syscolumns.NAME = B.NAME ) AS columnKey, '' AS extra FROM (SELECT NAME, object_id FROM sys.TABLES UNION ALL SELECT NAME, object_id FROM sys.views) a INNER JOIN sys.COLUMNS b ON b.object_id = a.object_id LEFT JOIN sys.types ON b.user_type_id = sys.types.user_type_id LEFT JOIN sys.extended_properties c ON c.major_id = b.object_id AND c.minor_id = b.column_id WHERE a.NAME = #{tableName} AND sys.types.NAME != 'sysname'")
    List<ColumnDTO> queryColumns(String tableName);

    @Override
    @Select("SELECT name, comment, engine, createTime FROM (SELECT cast(so.NAME AS VARCHAR(500)) AS name, 'mssql' AS engine, cast(sep.VALUE AS VARCHAR(500)) AS comment, getDate() AS createTime FROM sysobjects so LEFT JOIN sys.extended_properties sep ON sep.major_id = so.id AND sep.minor_id = 0 WHERE (xtype = 'U' OR xtype = 'v')) t WHERE t.name = #{tableName}")
    TableDTO queryTable(String tableName);
}
