package com.rockcandy.common.utils;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.config.TemplatePropertiesConfig;
import com.rockcandy.common.constants.EncodingConstants;
import com.rockcandy.common.constants.ExceptionConstants;
import com.rockcandy.common.constants.TemplateConstants;
import com.rockcandy.common.constants.TypeConstants;
import com.rockcandy.common.exception.ServiceException;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.common.service.GeneratorService;
import com.rockcandy.modules.mysql.service.MysqlGeneratorService;
import com.rockcandy.modules.oracle.service.OracleGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
    private static PropertiesConfig defaultConfig;

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
        TemplatePropertiesConfig config = new TemplatePropertiesConfig(defaultConfig);
        disposeConfig(config);
        config.setTableInfo(table);
        config.setHasBigDecimal(hasBigDecimal.get());
        VelocityContext velocityContext = new VelocityContext(BeanUtils.bean2Map(config));
        zip(velocityContext, table, zip);
    }

    /**
     * 封装模板数据，对配置数据进行处理
     */
    private static void disposeConfig(TemplatePropertiesConfig config) {
        // 截取包路径最后一位为服务层基类名称
        if (StringUtils.isNotBlank(defaultConfig.getBaseServiceConfig().getPackageRelativePath())) {
            config.getBaseServiceConfig().setExist(true);
            config.getBaseServiceConfig().setClassName(defaultConfig.getBaseServiceConfig().getPackageRelativePath()
                    .substring(defaultConfig.getBaseServiceConfig().getPackageRelativePath().indexOf(".")));
        }
        // 截取包路径最后一位为实体类基类名称
        if (StringUtils.isNotBlank(defaultConfig.getBaseEntityConfig().getPackageRelativePath())) {
            config.getBaseEntityConfig().setExist(true);
            config.getBaseEntityConfig().setClassName(defaultConfig.getBaseEntityConfig().getPackageRelativePath()
                    .substring(defaultConfig.getBaseEntityConfig().getPackageRelativePath().indexOf(".")));
        }
        // 截取包路径最后一位为控制层返回结果对象
        if (StringUtils.isNotBlank(defaultConfig.getCtrlResultConfig().getPackageRelativePath())) {
            config.getCtrlResultConfig().setExist(true);
            config.getCtrlResultConfig().setClassName(defaultConfig.getCtrlResultConfig().getPackageRelativePath()
                    .substring(defaultConfig.getCtrlResultConfig().getPackageRelativePath().indexOf(".")));
        }else {
            config.getCtrlResultConfig().setClassName("Map<String,Object>");
        }
    }

    private static void zip(VelocityContext velocityContext, TableDO table, ZipOutputStream zip) {
        for (String template : TemplateConstants.Templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, EncodingConstants.Utf8);
            tpl.merge(velocityContext, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, table.getUpperClassName(),
                        defaultConfig.getPackagePath(), defaultConfig.getModuleName()))));
                IOUtils.write(sw.toString(), zip, EncodingConstants.Utf8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new ServiceException(table.getTableName() + ExceptionConstants.RenderTemplate, e);
            }
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        StringBuilder packagePath = new StringBuilder("main").append(File.separator).append("java").append(File.separator);
        if (StringUtils.isNotBlank(packageName)) {
            packagePath.append(packageName.replace(".", File.separator)).append(File.separator).append(moduleName).append(File.separator);
        }
        switch (template) {
            case TemplateConstants.Entity:
                return packagePath.append("domain").append(File.separator).append(className).append(".java").toString();
            case TemplateConstants.Mapper:
                return packagePath.append("dao").append(File.separator).append(className).append("Dao.java").toString();
            case TemplateConstants.Service:
                return packagePath.append("service").append(File.separator).append(File.separator).append(className).append("Service.java").toString();
            case TemplateConstants.Controller:
                return packagePath.append("controller").append(File.separator).append(className).append("Controller.java").toString();
            case TemplateConstants.MapperXml:
                return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
            case TemplateConstants.Menu:
                return className.toLowerCase() + "_menu.sql";
            default:
                return null;
        }
    }

    /**
     * 生成代码入口
     */
    public static void generatorCode() {
        defaultConfig = SpringContextUtils.getBean(PropertiesConfig.class);
        checkConfig();
        GeneratorService generatorService;
        if (TypeConstants.Oracle.equalsIgnoreCase(defaultConfig.getSqlType())) {
            generatorService = SpringContextUtils.getBean(OracleGeneratorService.class);
        } else {
            generatorService = SpringContextUtils.getBean(MysqlGeneratorService.class);
        }
        generatorService.generatorCode();
    }

    /**
     * 检测配置信息是否满足生成代码条件
     */
    private static void checkConfig() {
        if (StringUtils.isBlank(defaultConfig.getFileOutputPath())) {
            throw new ServiceException(ExceptionConstants.RequireOutPath);
        }
        if (defaultConfig.getGeneratorTableName() == null || defaultConfig.getGeneratorTableName().length == 0) {
            throw new ServiceException(ExceptionConstants.RequireTable);
        }
    }
}
