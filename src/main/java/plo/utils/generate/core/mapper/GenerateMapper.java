package plo.utils.generate.core.mapper;

import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;

import java.util.List;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成器mapper
 * @since 2020-03-05 14:05
 */
public interface GenerateMapper {

    /**
     * 根据数据库表名获取列信息
     *
     * @param tableName 表名
     * @return 该数据库表的列信息
     */
    List<ColumnDTO> queryColumns(String tableName);

    /**
     * 根据数据库表名获取表信息
     *
     * @param tableName 表名
     * @return 该数据库表信息
     */
    TableDTO queryTable(String tableName);
}
