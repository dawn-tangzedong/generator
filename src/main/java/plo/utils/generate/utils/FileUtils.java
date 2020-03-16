package plo.utils.generate.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import plo.utils.generate.enums.GenerateModuleEnums;
import plo.utils.generate.factory.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote
 * @since 2020-03-06 21:48
 */
public class FileUtils {
    /**
     * 读取json文件
     *
     * @param filePath json文件路径
     * @return json字符串
     */
    static JSONObject readJson(String filePath) {
        try {
            String json = FileUtils.readJson(ClassLoader.getSystemResourceAsStream(filePath));
            return JSON.parseObject(json);
        } catch (Exception e) {
            throw new RuntimeException("读取json文件失败！", e);
        }
    }

    /**
     * 读取json文件
     *
     * @param file json文件
     * @return json字符串
     */
    private static String readJson(InputStream file) {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException("读取json文件失败！", e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    /**
     * 根据模板获取输出路径
     *
     * @param module 模块
     * @return 输出路径
     */
    private static String getFileOutputPath(GenerateModuleEnums module) {
        String path = ConfigFactory.pathConfig.getRootPath();
        String modulePath;
        String moduleName = module.name().toLowerCase();
        switch (module) {
            case Domain:
                modulePath = ConfigFactory.pathConfig.getDomainPath();
                break;
            case Mapper:
                modulePath = ConfigFactory.pathConfig.getMapperPath();
                break;
            case Controller:
                modulePath = ConfigFactory.pathConfig.getControllerPath();
                break;
            case Service:
                modulePath = ConfigFactory.pathConfig.getServicePath();
                break;
            case ServiceFactory:
                modulePath = ConfigFactory.pathConfig.getFactoryPath();
                moduleName = "factory";
                break;
            case Xml:
                if (StringUtils.isBlank(ConfigFactory.pathConfig.getXmlPath())) {
                    return path.concat("/src/main/resources/mapper");
                }
                return path.concat("/src/main/resources")
                        .concat(ConfigFactory.pathConfig.getXmlPath());
            default:
                throw new RuntimeException();
        }
        if (StringUtils.isBlank(modulePath)) {
            return path.concat("/src/main/java/").concat(ConfigFactory.pathConfig.getPackagePath())
                    .concat("/").concat(ConfigFactory.pathConfig.getModuleName())
                    .concat("/").concat(moduleName)
                    .replace(".", "/");
        }
        return path.concat("/src/main/java/").concat(modulePath).replace(".", "/");
    }

    /**
     * 根据模块和表名生成文件的路径【包含文件名称】
     *
     * @param module   模块
     * @param fileName 文件名称
     * @return 文件名称
     */
    private static String getFilePath(GenerateModuleEnums module, String fileName) {
        return FileUtils.getFileOutputPath(module).concat("/").concat(FileUtils.getFileName(module, fileName));
    }

    /**
     * 根据模块和表名生成文件的名称
     *
     * @param module   模块
     * @param fileName 文件名称
     * @return 文件名称
     */
    public static String getFileName(GenerateModuleEnums module, String fileName) {
        switch (module) {
            case ServiceFactory:
                return fileName.concat("ServiceFactory.java");
            case Xml:
                return fileName.concat("Mapper.xml");
            case Domain:
                return fileName.concat(".java");
            default:
                return fileName.concat(module.name()).concat(".java");
        }
    }

    /**
     * 根据模块以及表名生成文件
     *
     * @param module   模块
     * @param fileName 文件名称
     * @return 文件
     * @throws IOException 文件io流异常
     */
    static File getFile(GenerateModuleEnums module, String fileName) throws IOException {
        File dir = new File(FileUtils.getFilePath(module, fileName));
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        if (!dir.exists()) {
            dir.createNewFile();
        }
        return dir;
    }


    /**
     * @return 获取包路径
     */
    static String getPackagePath(GenerateModuleEnums module) {
        String modulePath;
        String moduleName = module.name().toLowerCase();
        switch (module) {
            case Domain:
                modulePath = ConfigFactory.pathConfig.getDomainPath();
                break;
            case Mapper:
                modulePath = ConfigFactory.pathConfig.getMapperPath();
                break;
            case Controller:
                modulePath = ConfigFactory.pathConfig.getControllerPath();
                break;
            case Service:
                modulePath = ConfigFactory.pathConfig.getServicePath();
                break;
            case ServiceFactory:
                modulePath = ConfigFactory.pathConfig.getFactoryPath();
                moduleName = "factory";
                break;
            default:
                throw new RuntimeException();
        }
        if (StringUtils.isBlank(modulePath)) {
            return ConfigFactory.pathConfig.getPackagePath().concat("/")
                    .concat(ConfigFactory.pathConfig.getModuleName()).concat("/")
                    .concat(moduleName).replace("/", ".");
        }
        return modulePath.replace("/", ".");
    }
}
