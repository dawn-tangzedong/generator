package com.rockcandy.config;

import com.rockcandy.domain.TableDO;
import com.rockcandy.utils.DateUtils;
import lombok.Data;

import java.util.Date;

@Data
public class TemplatePropertiesConfig extends PropertiesConfig {
    private TableDO tableInfo;
    private boolean hasBigDecimal;
    private String datetime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);

    public TemplatePropertiesConfig(PropertiesConfig propertiesConfig) {
        super.setAuthor(propertiesConfig.getAuthor());
        super.setColumnPrefix(propertiesConfig.getColumnPrefix());
        super.setEmail(propertiesConfig.getEmail());
        super.setFileOutputPath(propertiesConfig.getFileOutputPath());
        super.setMainPath(propertiesConfig.getMainPath());
        super.setModuleName(propertiesConfig.getModuleName());
        super.setPackagePath(propertiesConfig.getPackagePath());
        super.setTablePrefix(propertiesConfig.getTablePrefix());
    }

}
