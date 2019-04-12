package com.rockcandy.modules.oracle.service;

import com.rockcandy.common.utils.GenUtils;
import com.rockcandy.common.utils.NameUtils;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.common.service.GeneratorService;
import com.rockcandy.modules.oracle.dao.OracleGeneratorDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Oracle 数据库代码生成器服务层
 * @date 2019-4-9 17:12:01
 */
@Service
public class OracleGeneratorService extends GeneratorService<OracleGeneratorDao> {

    private static final String CONFIG = "mysqlGenerator.properties";

    @Override
    protected void disposeColumns(TableDO table, List<ColumnDO> columns, AtomicBoolean hasBigDecimal) {
        String primaryKey = dao.getPrimaryKey(table.getTableName());
        columns.forEach(column -> {
            column.setUpperAttrName(NameUtils.convertName(column.getColumnName(), StringUtils.split(defaultConfig.getColumnPrefix(), ",")));
            column.setLowerAttrName(StringUtils.uncapitalize(column.getUpperAttrName()));
            String attrType;
            attrType = GenUtils.getConfig(CONFIG).getString(column.getDataType(), "unknowType");
            column.setAttrType(attrType);
            // 长整型判断
            if (column.getDataType().equals("NUMBER")) {
                if (column.getCharacterOctetLength().equals(1)) {
                    attrType = "Boolean";
                } else if (column.getCharacterOctetLength() < 10) {
                    attrType = "Integer";
                } else if (column.getCharacterOctetLength() < 19) {
                    attrType = "Long";
                } else {
                    attrType = "BigDecimal";
                    if (!hasBigDecimal.get()) {
                        hasBigDecimal.set(true);
                    }
                }
            } else {
                attrType = GenUtils.getConfig("oracleGenerator.properties").getString(column.getDataType(), "unknowType");
            }
            if (column.getColumnName().equals(primaryKey)) {
                table.setPk(column);
            }
            column.setAttrType(attrType);
        });
        table.setColumns(columns);
    }
}
