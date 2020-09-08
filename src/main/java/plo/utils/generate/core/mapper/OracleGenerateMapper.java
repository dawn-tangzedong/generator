package plo.utils.generate.core.mapper;

import org.apache.ibatis.annotations.Select;
import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;

import java.util.List;

/**
 * Oracle代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-24
 */
public interface OracleGenerateMapper extends GenerateMapper{

    @Override
    @Select("SELECT temp.column_name name, temp.data_type dataType, temp.comments comment, CASE temp.constraint_type WHEN 'P' THEN 'PRI' WHEN 'C' THEN 'UNI' ELSE '' END 'COLUMNKEY', '' 'EXTRA' FROM ( SELECT col.column_id, col.column_name, col.data_type, colc.comments, uc.constraint_type,-- 去重 row_number() over (PARTITION BY col.column_name ORDER BY uc.constraint_type DESC) AS row_flg FROM user_tab_columns col LEFT JOIN user_col_comments colc ON colc.table_name = col.table_name AND colc.column_name = col.column_name LEFT JOIN user_cons_columns ucc ON ucc.table_name = col.table_name AND ucc.column_name = col.column_name LEFT JOIN user_constraints uc ON uc.constraint_name = ucc.constraint_name WHERE col.table_name = upper( #{tableName}) ) temp WHERE temp.row_flg = 1 ORDER BY temp.column_id")
    List<ColumnDTO> queryColumns(String tableName);

    @Override
    @Select("SELECT dt.table_name name, dtc.comments comment, dt.last_analyzed createTime FROM user_tables dt, user_tab_comments dtc WHERE dt.table_name = dtc.table_name AND dt.table_name = UPPER(#{tableName})")
    TableDTO queryTable(String tableName);
}
