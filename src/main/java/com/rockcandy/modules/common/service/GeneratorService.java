package com.rockcandy.modules.common.service;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.utils.GenUtils;
import com.rockcandy.modules.common.dao.GeneratorDao;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Code Generator Service
 * @since 2019/4/9 16:44
 */
@Service
public class GeneratorService<Dao extends GeneratorDao> {

    @Autowired
    private Dao dao;

    @Autowired
    private PropertiesConfig propertyConfig;

    /**
     * 生成代码，并以流的形式返回
     *
     * @return 生成代码后的流
     */
    public byte[] generatorCode() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : propertyConfig.getGeneratorTableName().split(",")) {
            //查询表信息
            TableDO table = dao.queryTable(tableName);
            //查询列信息
            List<ColumnDO> columns = dao.queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
};
