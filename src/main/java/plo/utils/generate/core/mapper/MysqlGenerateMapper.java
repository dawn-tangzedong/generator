package plo.utils.generate.core.mapper;


import org.apache.ibatis.annotations.Select;
import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;

import java.util.List;

/**
 * MySQL代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-24
 */
public interface MysqlGenerateMapper extends GenerateMapper {

    @Override
    @Select("SELECT column_name name, data_type dataType, column_comment comment, column_key columnKey, extra FROM information_schema.columns WHERE table_name = #{tableName} AND table_schema = (SELECT database()) ORDER BY ordinal_position")
    List<ColumnDTO> queryColumns(String tableName);

    @Override
    @Select("SELECT table_name name, engine, table_comment comment, create_time createTime FROM information_schema.TABLES WHERE table_schema = (SELECT DATABASE ()) AND table_name = #{tableName}")
    TableDTO queryTable(String tableName);
}
