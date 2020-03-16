package plo.utils.generate.factory;

import plo.utils.generate.config.BasicBeanConfig;
import plo.utils.generate.config.DatabaseConfig;
import plo.utils.generate.config.PathConfig;
import plo.utils.generate.config.SystemConfig;
import plo.utils.generate.utils.ConfigurationUtils;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 配置工厂
 * @since 2020-03-05 18:27
 */
public class ConfigFactory {

    public static BasicBeanConfig basicBeanConfig;
    public static DatabaseConfig databaseConfig;
    public static PathConfig pathConfig;
    public static SystemConfig systemConfig;

    /**
     * 初始化配置文件
     */
    static {
        ConfigFactory.basicBeanConfig = ConfigurationUtils.getConfig("basic", BasicBeanConfig.class);
        ConfigFactory.databaseConfig = ConfigurationUtils.getConfig("database", DatabaseConfig.class);
        ConfigFactory.systemConfig = ConfigurationUtils.getConfig("system", SystemConfig.class);
        ConfigFactory.pathConfig = ConfigurationUtils.getConfig("path", PathConfig.class);
    }
}
