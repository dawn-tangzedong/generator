package plo.utils.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成路径相关配置类
 * @since 2020-03-05 09:07
 */
@Data
@Accessors(chain = true)
public class PathConfig {
    /**
     * 项目路径，默认为项目的根目录
     */
    private String rootPath;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 控制层路径，默认情况 rootPath / packagePath / moduleName / controller
     * 当控制层有值（用户指定路径），控制层路径为 rootPath / controllerPath
     */
    private String controllerPath;

    /**
     * 服务层路径，默认情况下是 rootPath / packagePath / moduleName / service
     * 当服务层有值（用户指定路径），服务层路径为 rootPath / servicePath
     */
    private String servicePath;

    /**
     * 持久层路径，默认情况下是 rootPath / packagePath / moduleName / mapper
     * 当持久层有值（用户指定路径），持久层路径为 rootPath / mapperPath
     */
    private String mapperPath;

    /**
     * 实体层路径，默认情况下是 rootPath / packagePath / moduleName / domain
     * 当实体层有值（用户指定路径），实体层路径为 rootPath / mapperPath
     */
    private String domainPath;

    /**
     * 服务工厂类路径，默认情况下是 rootPath / packagePath / moduleName / factory
     * 当服务工厂类有值（用户指定路径），实体层路径为 rootPath / factoryPath
     */
    private String factoryPath;

    /**
     * mybatis配置文件路径，默认情况下是 rootPath / src / main / resources / mapper /moduleName
     * 当mybatis配置文件有值（用户指定路径），实体层路径为 rootPath / src / main / resources / xmlPath
     */
    private String xmlPath;

    /**
     * 初始化 rootPath 为项目的根路径，该路径是绝对路径
     */
    private void init() {
        rootPath = System.getProperty("user.dir");
    }

    /**
     * 构造函数，函数调用初始化
     */
    public PathConfig() {
        init();
    }

    public void setRootPath(String rootPath) {
        if (StringUtils.isBlank(rootPath) || rootPath.equals("/")) {
            this.rootPath = System.getProperty("user.dir");
        }
    }
}
