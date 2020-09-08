package plo.utils.generate.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import plo.utils.generate.config.PathConfig;
import plo.utils.generate.dto.TableDTO;
import plo.utils.generate.enums.GenerateModuleEnums;
import plo.utils.generate.factory.ConfigFactory;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote
 * @since 2020-03-06 09:26
 */
@Slf4j
public class FreeMarkerUtils {

    /**
     * Configuration 的创建代价很高，不需要重复创建实例，尤其是会丢失缓存
     */
    public static final Configuration CONFIG;

    static {
        // 对Config进行初始化
        CONFIG = new Configuration(Configuration.getVersion());
        /// 打成jar包找不到文件
        //CONFIG.setDirectoryForTemplateLoading(new File(FreeMarkerUtils.class.getResource("/templates").getPath()));
        CONFIG.setClassForTemplateLoading(FreeMarkerUtils.class, "/templates");
        CONFIG.setDefaultEncoding("UTF-8");
        CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * 生成公共的模板，例如【Factory】
     *
     * @param tables 所有的表集合
     * @param module 模块
     */
    public static void generateCommentModule(List<TableDTO> tables, GenerateModuleEnums module) {
        try {
            Template template = FreeMarkerUtils.CONFIG.getTemplate(module.getPath());
            String fileName = DataUtils.camelCase(ConfigFactory.pathConfig.getModuleName());
            // 创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
            HashMap<String, Object> dataModel = new HashMap<String, Object>(16) {{
                // 数据库表信息
                put("tables", tables);
                put("data", new Date());
                // 获取所有的包路径
                put("packagePath", getPackagePath());
                put("module", ConfigFactory.pathConfig.getModuleName());
                put("fileName", fileName.concat(module.name()));
                put("author", ConfigFactory.systemConfig.getAuthor());
            }};
            // 创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
            Writer writer = new OutputStreamWriter(new FileOutputStream(FileUtils.getFile(module, fileName)));
            // 调用模板对象的process方法输出文件。
            template.process(dataModel, writer);
        } catch (Exception e) {
            log.error("load template error", e);
        }
    }

    /**
     * 根据模块和表生成模板，这里不能生成Factory
     *
     * @param table   数据库表信息【包含列】
     * @param modules 需要生成的模块
     */
    public static void generateTable(TableDTO table, GenerateModuleEnums[] modules) {
        Arrays.stream(modules).forEach(item -> {
            // Factory不能在这里生成
            if (item.equals(GenerateModuleEnums.ServiceFactory)) {
                return;
            }
            // 根据模板生成文件 加载一个模板，创建一个模板对象。
            try {
                Template template = FreeMarkerUtils.CONFIG.getTemplate(item.getPath());
                // 创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
                HashMap<String, Object> dataModel = new HashMap<String, Object>(16) {{
                    // 数据库表信息
                    put("table", table);
                    put("data", new Date());
                    // 获取所有的包路径
                    put("packagePath", getPackagePath());
                    // 配置信息
                    put("basicBeanConfig", ConfigFactory.basicBeanConfig);
                    put("databaseConfig", ConfigFactory.databaseConfig);
                    put("pathConfig", ConfigFactory.pathConfig);
                    put("systemConfig", ConfigFactory.systemConfig);

                }};
                // 创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
                Writer writer = new OutputStreamWriter(new FileOutputStream(FileUtils.getFile(item, table.getUpperClassName())));
                // 调用模板对象的process方法输出文件。
                template.process(dataModel, writer);
            } catch (Exception e) {
                log.error("load template error", e);
            }
        });
    }

    /**
     * 根据模块获取模块对应的包路径
     *
     * @return 包路径
     */
    public static PathConfig getPackagePath() {
        PathConfig packagePath = new PathConfig();
        for (GenerateModuleEnums module : GenerateModuleEnums.values()) {
            switch (module) {
                case Domain:
                    packagePath.setDomainPath(FileUtils.getPackagePath(module));
                    break;
                case Mapper:
                    packagePath.setMapperPath(FileUtils.getPackagePath(module));
                    break;
                case Controller:
                    packagePath.setControllerPath(FileUtils.getPackagePath(module));
                    break;
                case Service:
                    packagePath.setServicePath(FileUtils.getPackagePath(module));
                    break;
                case ServiceFactory:
                    packagePath.setFactoryPath(FileUtils.getPackagePath(module));
                default:
            }
        }
        return packagePath;
    }
}
