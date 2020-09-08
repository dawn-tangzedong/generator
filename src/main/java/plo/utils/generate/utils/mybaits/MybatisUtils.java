package plo.utils.generate.utils.mybaits;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import plo.utils.generate.core.mapper.*;
import plo.utils.generate.factory.ConfigFactory;

import javax.sql.DataSource;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote
 * @since 2020-03-11 16:55
 */
public class MybatisUtils {
    public static SqlSession SESSION;

    static {
        initSqlSessionFactory();
    }

    /**
     * 初始化MyBatis的{@link SqlSessionFactory}
     */
    private static void initSqlSessionFactory() {
        // 初始化mybatis
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(MybatisConfigProperty.getProperty());
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);

        // 加载mapper
        configuration.addMapper(MysqlGenerateMapper.class);
        configuration.addMapper(OracleGenerateMapper.class);
        configuration.addMapper(PostgreSqlGenerateMapper.class);
        configuration.addMapper(SqlServerGenerateMapper.class);
        configuration.addMapper(GenerateMapper.class);

        SESSION = new SqlSessionFactoryBuilder().build(configuration).openSession();
    }

    /**
     * 根据方言获取对应的mapper实例
     *
     * @return mapper实例
     */
    public static GenerateMapper getByDialect() {
        try {
            switch (ConfigFactory.databaseConfig.getDialect()) {
                case Mysql:
                    return SESSION.getMapper(MysqlGenerateMapper.class);
                case Oracle:
                    return SESSION.getMapper(OracleGenerateMapper.class);
                case SqlServer:
                    return SESSION.getMapper(SqlServerGenerateMapper.class);
                case PostgreSql:
                    return SESSION.getMapper(PostgreSqlGenerateMapper.class);
                default:
                    throw new IllegalArgumentException("dialect value is error");
            }
        } catch (Exception e) {
            throw new RuntimeException("sql session get mapper error", e);
        }
    }

}
