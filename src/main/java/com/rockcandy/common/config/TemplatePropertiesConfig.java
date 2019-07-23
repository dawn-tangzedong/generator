package com.rockcandy.common.config;

import com.rockcandy.common.utils.DateUtils;
import com.rockcandy.common.utils.NameUtils;
import com.rockcandy.modules.common.domain.TableDO;
import lombok.Data;

import java.util.Date;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 模板参数
 * @since 2019/4/9 9:21
 */
@Data
public class TemplatePropertiesConfig extends PropertiesConfig {
    private TableDO tableInfo;
    private boolean hasBigDecimal;
    private String datetime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);

    private String serviceFactory;

    /**
     * 根据父类，同步父类数据
     *
     * @param propertiesConfig the properties config
     */
    public TemplatePropertiesConfig(PropertiesConfig propertiesConfig) {
        super.setAuthor(propertiesConfig.getAuthor());
        super.setSqlType(propertiesConfig.getSqlType());
        super.setMainPath(propertiesConfig.getMainPath());
        super.setModuleName(propertiesConfig.getModuleName());
        super.setPackagePath(propertiesConfig.getPackagePath());
        super.setTablePrefix(propertiesConfig.getTablePrefix());
        super.setColumnPrefix(propertiesConfig.getColumnPrefix());
        super.setFileOutputPath(propertiesConfig.getFileOutputPath());
        super.setIgnoreAttributes(propertiesConfig.getIgnoreAttributes());
        super.setUsualFrameConfig(propertiesConfig.getUsualFrameConfig());
        super.setCtrlResultConfig(propertiesConfig.getCtrlResultConfig());
        super.setBaseEntityConfig(propertiesConfig.getBaseEntityConfig());
        super.setBaseServiceConfig(propertiesConfig.getBaseServiceConfig());
        super.setGeneratorTableName(propertiesConfig.getGeneratorTableName());
        this.serviceFactory = NameUtils.camelCase(propertiesConfig.getModuleName());
    }


}
