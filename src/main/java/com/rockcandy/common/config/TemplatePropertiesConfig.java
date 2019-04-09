package com.rockcandy.common.config;

import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.common.utils.DateUtils;
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

    /**
     * 根据父类，同步父类数据
     *
     * @param propertiesConfig the properties config
     */
    public TemplatePropertiesConfig(PropertiesConfig propertiesConfig) {
        super.setAuthor(propertiesConfig.getAuthor());
        super.setColumnPrefix(propertiesConfig.getColumnPrefix());
        super.setEmail(propertiesConfig.getEmail());
        super.setFileOutputPath(propertiesConfig.getFileOutputPath());
        super.setMainPath(propertiesConfig.getMainPath());
        super.setModuleName(propertiesConfig.getModuleName());
        super.setPackagePath(propertiesConfig.getPackagePath());
        super.setTablePrefix(propertiesConfig.getTablePrefix());
        super.setUseShiro(propertiesConfig.isUseShiro());
        super.setUseSwagger(propertiesConfig.isUseSwagger());
    }

}
