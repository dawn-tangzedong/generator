package plo.utils.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;
import plo.utils.generate.enums.SqlDialectEnums;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成数据库相关配置
 * @since 2020-03-05 10:12
 */
@Data
@Accessors(chain = true)
public class DatabaseConfig {

    /**
     * 表前缀过滤
     */
    private String[] tablePrefix;

    /**
     * 列前缀过滤
     */
    private String[] columnPrefix;

    /**
     * 使用基类忽略的属性，可以自定义
     */
    private String[] ignoreAttributes;

    /**
     * 需要生成的表名
     */
    private String[] generatorTableName;

    /**
     * 版本的字段
     */
    private String version;

    /**
     * 删除的字段
     */
    private String delFlag;

    /**
     * 方言，可选类型参考{@link SqlDialectEnums}，默认参数为 Mysql
     */
    private SqlDialectEnums dialect;

    /**
     * 初始化函数，指定方言为 Mysql
     */
    private void init() {
        dialect = SqlDialectEnums.Mysql;
    }

    /**
     * 构造函数，执行初始化函数
     */
    public DatabaseConfig() {
        init();
    }
}
