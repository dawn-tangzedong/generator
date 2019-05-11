package com.rockcandy.modules.mysql.service;

import com.rockcandy.common.utils.GeneratorTypeUtils;
import com.rockcandy.common.utils.NameUtils;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.common.service.GeneratorService;
import com.rockcandy.modules.mysql.dao.MysqlGeneratorDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Mysql 数据库代码生成器服务层
 * @date 22019-4-9 17:12:11
 */
@Service
public class MysqlGeneratorService extends GeneratorService<MysqlGeneratorDao> {

    /**
     * 处理数据库列信息
     * @param table         数据库表
     * @param columns       数据库列集合
     * @param hasBigDecimal 是否有bigDecimal数据格式
     */
    @Override
    protected void disposeColumns(TableDO table, List<ColumnDO> columns, AtomicBoolean hasBigDecimal) {
        columns.forEach(column -> {
            column.setUpperAttrName(NameUtils.convertName(column.getColumnName(), StringUtils.split(defaultConfig.getColumnPrefix(), ",")));
            column.setLowerAttrName(StringUtils.uncapitalize(column.getUpperAttrName()));
            String attrType;
            // 我比较喜欢用tinyint(1)作为boolean类型，然后根据配置文件查找对应数据类型
            if ("tinyint(1)".equals(column.getColumnType())) {
                attrType = "Boolean";
            } else {
                attrType = GeneratorTypeUtils.Mysql.get(column.getDataType());
                attrType = attrType == null ? "String" : attrType;
            }
            if (!hasBigDecimal.get() && "BigDecimal".equals(attrType)) {
                hasBigDecimal.set(true);
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey()) && table.getPk() == null) {
                table.setPk(column);
            }
            column.setAttrType(attrType);
        });
        table.setColumns(columns);
    }
}
