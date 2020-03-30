package plo.utils.generate.utils;

import lombok.extern.slf4j.Slf4j;
import plo.utils.generate.dto.TableDTO;
import plo.utils.generate.enums.GenerateModuleEnums;
import plo.utils.generate.factory.ConfigFactory;
import plo.utils.generate.factory.GenerateServiceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成代码工具类
 * @since 2020-03-05 17:20
 */
@Slf4j
public class GenerateUtils {

    /**
     * 生成代码到项目中
     */
    public static void generate() {
        // 必要参数数据校验
        ConfigurationUtils.configDataVerify();
        // 收集table 的数据
        List<TableDTO> tables = new ArrayList<>(ConfigFactory.databaseConfig.getGeneratorTableName().length);
        for (String tableName : ConfigFactory.databaseConfig.getGeneratorTableName()) {
            // 从数据库中获取表信息
            TableDTO table = GenerateServiceFactory.generateService.queryTable(tableName);
            // 判断表是否存在，不存在则抛出异常：NullPointerException
            if (Objects.isNull(table)) {
                log.error("{} is not found", tableName);
                throw new NullPointerException(tableName);
            }
            tables.add(table);
            // 从数据库中获取列信息
            table.setColumns(GenerateServiceFactory.generateService.queryColumns(tableName));
            // 对数据库表进行数据处理
            DataUtils.process(table);
            FreeMarkerUtils.generateTable(table, ConfigFactory.systemConfig.getModules());
        }
        // 如果生成包含了Factory
        if (Arrays.asList(ConfigFactory.systemConfig.getModules()).contains(GenerateModuleEnums.ServiceFactory)) {
            FreeMarkerUtils.generateCommentModule(tables, GenerateModuleEnums.ServiceFactory);
        }
    }
}
