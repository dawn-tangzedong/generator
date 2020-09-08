package plo.utils.generate.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import plo.utils.generate.dto.TableDTO;
import plo.utils.generate.factory.ConfigFactory;

import java.util.Objects;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 数据工具
 * @since 2020-03-06 14:26
 */
public class DataUtils {

    /**
     * 数据库表数据处理
     *
     * @param table 表
     */
    static void process(TableDTO table) {
        // 设置表转换java bean 的类名称
        table.setUpperClassName(DataUtils.convertName(table.getName(), ConfigFactory.databaseConfig.getTablePrefix()))
                .setLowerClassname(StringUtils.uncapitalize(table.getUpperClassName()));
        table.getColumns().forEach(column -> {
            // 设置属性名称
            column.setUpperAttrName(DataUtils.convertName(column.getName(), ConfigFactory.databaseConfig.getColumnPrefix()))
                    .setLowerAttrName(StringUtils.uncapitalize(column.getUpperAttrName()));
            // 设置表的主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey()) && table.getPk() == null) {
                table.setPk(column);
            }
            // 设置属性的数据类型
            String attrType = FileUtils.readJson("json/dataType.json").getString(column.getDataType());
            if (StringUtils.isBlank(attrType)) {
                attrType = "unknowType";
            }
            // 判断是否存在BigDecimal数据类型
            if (!table.getHasBigDecimal() && attrType.equals("BigDecimal")) {
                table.setHasBigDecimal(true);
            }
            column.setAttrType(attrType);
            // 乐观锁字段
            if (!table.getHasVersion() && column.getName().equals(ConfigFactory.databaseConfig.getVersion())) {
                column.setVersion(true);
                table.setHasVersion(true);
            }
            // 逻辑删除字段
            if (!table.getHasDelFlag() && column.getName().equals(ConfigFactory.databaseConfig.getDelFlag())) {
                column.setDelFlag(true);
                table.setHasDelFlag(true);
            }
        });
        if (Objects.isNull(table.getPk())) {
            throw new RuntimeException("生成失败：表[" + table.getName() + "]缺少主键");
        }
    }

    /**
     * 数据库表名/列名转换成java类名/属性名，并循环过滤掉 prefix
     *
     * @param name     表名
     * @param prefixes 表名需要过滤的字符串
     * @return 转换后的结果
     */
    private static String convertName(String name, String[] prefixes) {
        if (prefixes.length > 0) {
            // 循环遍历，过滤掉 prefix
            for (String prefix : prefixes) {
                name = name.replaceFirst(prefix, "");
            }
        }
        return camelCase(name);
    }


    /**
     * 驼峰命令
     */
    public static String camelCase(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }
}
