package com.rockcandy.common.utils;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.config.TemplatePropertiesConfig;
import com.rockcandy.common.exception.ServiceException;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.common.service.GeneratorService;
import com.rockcandy.modules.mysql.service.MysqlGeneratorService;
import com.rockcandy.modules.oracle.service.OracleGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author tangzedong.programmer@gmail.com
 * @date 2019-4-8 23:14:01
 */
@Slf4j
public class GenUtils {
    private static PropertiesConfig defaultConfig = SpringContextUtils.getBean(PropertiesConfig.class);

    /**
     * 获取模板信息
     *
     * @return the templates
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/Controller.java.vm");
//		templates.add("template/Service.java.vm");
//		templates.add("template/list.html.vm");
//		templates.add("template/list.js.vm");
        templates.add("template/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     *
     * @param table         表信息
     * @param hasBigDecimal 是否有BigDecimal类型
     * @param zip           压缩包
     */
    public static void generatorCode(TableDO table, AtomicBoolean hasBigDecimal, ZipOutputStream zip) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        // 封装模板数据
        TemplatePropertiesConfig config = disposeConfig();
        config.setTableInfo(table);
        config.setHasBigDecimal(hasBigDecimal.get());
        VelocityContext velocityContext = new VelocityContext(BeanUtils.bean2Map(config));
        zip(velocityContext, table, zip);
    }

    /**
     * 封装模板数据
     *
     * @return 模板配置数据
     */
    private static TemplatePropertiesConfig disposeConfig() {
        TemplatePropertiesConfig config = new TemplatePropertiesConfig(defaultConfig);
        // 截取包路径最后一位为服务层基类名称
        if (StringUtils.isNotBlank(defaultConfig.getBaseServicePackage())) {
            config.setBaseService(defaultConfig.getBaseServicePackage()
                    .substring(config.getBaseEntityPackage().lastIndexOf(".") + 1, config.getBaseEntityPackage().length()));
            config.setHasBaseService(true);
        }
        // 截取包路径最后一位为实体类基类名称
        if (StringUtils.isNotBlank(defaultConfig.getBaseEntityPackage())) {
            config.setBaseEntity(defaultConfig.getBaseEntityPackage()
                    .substring(config.getBaseEntityPackage().lastIndexOf(".") + 1, config.getBaseEntityPackage().length()));
            config.setHasBaseEntity(true);
        }
        // 如果忽略的字段不为空，根据“,”拆分为数组
        if (StringUtils.isNotBlank(defaultConfig.getIgnoreAttribute())) {
            config.setIgnoreAttributes(defaultConfig.getIgnoreAttribute().split(","));
        }
        return config;
    }

    private static void zip(VelocityContext velocityContext, TableDO table, ZipOutputStream zip) {
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(velocityContext, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, table.getUpperClassName(),
                        defaultConfig.getPackagePath(), defaultConfig.getModuleName()))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {

                throw new ServiceException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }


    /**
     * 获取配置信息
     */
    public static Configuration getConfig(String config) {
        try {
            return new PropertiesConfiguration(config);
        } catch (ConfigurationException e) {
            throw new ServiceException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "domain" + File.separator + className + ".java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

//		if (template.contains("Service.java.vm" )) {
//			return packagePath + "service" + File.separator + className + "Service.java";
//		}

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + File.separator + className + "Service.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + ".html";
        }

        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "statics" + File.separator + "js" + File.separator
                    + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + ".js";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

    public static void generatorCode() {
        GeneratorService generatorService;
        if ("Oracle".equalsIgnoreCase(defaultConfig.getSqlType())) {
            generatorService = SpringContextUtils.getBean(OracleGeneratorService.class);
        } else {
            generatorService = SpringContextUtils.getBean(MysqlGeneratorService.class);
        }
        generatorService.generatorCode();
    }
}
