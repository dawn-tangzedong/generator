package plo.utils.generate.utils.mybaits;

import com.alibaba.fastjson.JSONObject;
import plo.utils.generate.factory.ConfigFactory;
import plo.utils.generate.utils.ConfigurationUtils;

import java.util.Properties;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote
 * @since 2020-03-11 17:04
 */
public class MybatisConfigProperty {
    /**
     * @return 获取连接数据库的参数对象
     */
    public static Properties getProperty() {
        Properties properties = new Properties();
        JSONObject config = ConfigurationUtils.CONFIG.getJSONObject("mybatis");
        properties.setProperty("driver", ConfigFactory.databaseConfig.getDialect().getDriver());
        properties.setProperty("url", config.getString("url"));
        properties.setProperty("username", config.getString("username"));
        properties.setProperty("password", config.getString("password"));
        return properties;
    }
}
