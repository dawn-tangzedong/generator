package com.rockcandy.common.utils;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.config.TemplatePropertiesConfig;
import com.rockcandy.common.exception.ServiceException;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.common.service.GeneratorService;
import com.rockcandy.modules.mysql.service.MysqlGeneratorService;
import com.rockcandy.modules.oracle.dao.OracleGeneratorDao;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
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
//		templates.add("template/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     *
     * @param table   表信息
     * @param columns 表所有的列信息
     * @param zip     压缩包
     */
    public static void generatorCode(TableDO table,
                                     List<ColumnDO> columns, ZipOutputStream zip) {
        // 处理表数据，并判断是否有bigDecimal属性
        AtomicBoolean hasBigDecimal = disposeTableInfo(table, columns);
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        // 封装模板数据
        TemplatePropertiesConfig config = new TemplatePropertiesConfig(defaultConfig);
        config.setTableInfo(table);
        config.setHasBigDecimal(hasBigDecimal.get());
        VelocityContext velocityContext = new VelocityContext(BeanUtils.bean2Map(config));
        zip(velocityContext, table, zip);
    }


    /**
     * 处理数据库表、列信息
     *
     * @param table   数据库表
     * @param columns 数据库列集合
     * @return 数据库表结构是否存在bigDecimal数据类型
     */
    private static AtomicBoolean disposeTableInfo(TableDO table, List<ColumnDO> columns) {
        AtomicBoolean hasBigDecimal = new AtomicBoolean(false);
        // class名称转化驼峰以及变量名
        table.setUpperClassName(convertName(table.getTableName(), StringUtils.split(defaultConfig.getTablePrefix(), ",")));
        table.setLowerClassName(StringUtils.uncapitalize(table.getUpperClassName()));
        // 列信息处理
        columns.forEach(column -> {
            column.setUpperAttrName(convertName(column.getColumnName(), StringUtils.split(defaultConfig.getColumnPrefix(), ",")));
            column.setLowerAttrName(StringUtils.uncapitalize(column.getUpperAttrName()));
            String attrType;
            // 我比较喜欢用tinyint(1)作为boolean类型
            if ("tinyint(1)".equals(column.getColumnType())) {
                attrType = "Boolean";
            } else {
                attrType = getConfig().getString(column.getDataType(), "unknowType");
            }
            column.setAttrType(attrType);
            if (!hasBigDecimal.get() && "BigDecimal".equals(attrType)) {
                hasBigDecimal.set(true);
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey()) && table.getPk() == null) {
                table.setPk(column);
            }
        });
        table.setColumns(columns);
        return hasBigDecimal;
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
     * 驼峰命令
     */
    private static String camelCase(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
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
                name = name.replace(prefix, "");
            }
        }
        return camelCase(name);
    }

    /**
     * 获取配置信息
     */
    private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("mysqlGenerator.properties");
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
            return packagePath + "entity" + File.separator + className + ".java";
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

    private void generatorCode() {
        GeneratorService generatorService;
        if ("Oracle".equalsIgnoreCase(defaultConfig.getSqlTyp())) {
//            generatorService = SpringContextUtils.getBean(OracleGeneratorService.class);
        }else {

            generatorService = SpringContextUtils.getBean(MysqlGeneratorService.class);
        }
    }
}
