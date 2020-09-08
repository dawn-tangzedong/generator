package plo.utils.generate.core;

import plo.utils.generate.dto.ColumnDTO;
import plo.utils.generate.dto.TableDTO;
import plo.utils.generate.utils.mybaits.MybatisUtils;

import java.util.List;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote
 * @since 2020-03-05 14:04
 */
public class GenerateService {

    /**
     * 根据数据库表名获取列信息
     *
     * @param tableName 表名
     * @return 该数据库表的列信息
     */
    public List<ColumnDTO> queryColumns(String tableName) {
        return MybatisUtils.getByDialect().queryColumns(tableName);
    }

    /**
     * 根据数据库表名获取表信息
     *
     * @param tableName 表名
     * @return 该数据库表信息
     */
    public TableDTO queryTable(String tableName) {
        return MybatisUtils.getByDialect().queryTable(tableName);
    }

}
