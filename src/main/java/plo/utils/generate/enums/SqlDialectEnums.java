package plo.utils.generate.enums;

import lombok.Getter;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 数据库方言
 * @since 2020-03-05 10:17
 */
@Getter
public enum SqlDialectEnums {
    /**
     * Mysql
     */
    Mysql("com.mysql.jdbc.Driver"),

    /**
     * Oracle
     */
    Oracle("oracle.jdbc.OracleDriver"),

    /**
     * PostgreSql
     */
    PostgreSql("org.postgresql.Driver"),

    /**
     * SqlServer
     */
    SqlServer("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    private String driver;

    SqlDialectEnums(String driver) {
        this.driver = driver;
    }
}
