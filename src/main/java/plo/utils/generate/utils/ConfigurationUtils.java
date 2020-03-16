package plo.utils.generate.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import plo.utils.generate.factory.ConfigFactory;

import java.util.Objects;


/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 配置文件工具
 * @since 2020-03-05 18:49
 */
public class ConfigurationUtils {
    /**
     * 获取yml配置信息
     */
    public static final JSONObject CONFIG = new Yaml().loadAs(ClassLoader.getSystemClassLoader().getResourceAsStream("application-config.yml"), JSONObject.class);

    /**
     * 获取配置信息
     */
    public static Configuration getConfig(String fileName) {
        try {
            return new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取配置信息
     *
     * @param key    配置信息的key
     * @param tClass 配置信息的class
     * @param <T>    配置信息的类型
     * @return 配置信息
     */
    public static <T> T getConfig(String key, Class<T> tClass) {
        T t = ConfigurationUtils.CONFIG.getObject(key, tClass);
        if (Objects.isNull(t)) {
            try {
                return tClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("加载" + key + "配置异常");
            }
        }
        return t;
    }

    /**
     * 配置数据校验
     * 对于必须参数的数据校验
     */
    public static void configDataVerify() {
        if (ConfigFactory.databaseConfig.getGeneratorTableName() == null || ConfigFactory.databaseConfig.getGeneratorTableName().length == 0) {
            throw new RuntimeException("缺少必要参数:database.generator-table-name");
        }
        if (StringUtils.isBlank(ConfigFactory.pathConfig.getPackagePath())) {
            throw new RuntimeException("缺少必要参数:path.package-path");
        }
        JSONObject config = ConfigurationUtils.CONFIG.getJSONObject("mybatis");
        if (StringUtils.isBlank(config.getString("username"))) {
            throw new RuntimeException("缺少必要参数:mybatis.username");
        }
        if (StringUtils.isBlank(config.getString("password"))) {
            throw new RuntimeException("缺少必要参数:mybatis.password");
        }
        if (StringUtils.isBlank(config.getString("driver"))) {
            throw new RuntimeException("缺少必要参数:mybatis.driver");
        }
        if (StringUtils.isBlank(config.getString("url"))) {
            throw new RuntimeException("缺少必要参数:mybatis.url");
        }
    }
}
