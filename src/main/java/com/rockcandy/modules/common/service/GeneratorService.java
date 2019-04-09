package com.rockcandy.modules.common.service;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.utils.GenUtils;
import com.rockcandy.modules.common.dao.GeneratorDao;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Code Generator Service
 * @since 2019/4/9 16:44
 */
@Service
@Slf4j
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
    public void generatorCode() {
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
        // 把文件流写入磁盘中
        writeDisk(outputStream);
    }

    /**
     * 文件流写入磁盘中
     *
     * @param zipFile 输出流
     */
    private void writeDisk(ByteArrayOutputStream zipFile) {
        FileOutputStream zip = null;
        try {
            zip = new FileOutputStream(propertyConfig.getFileOutputPath() + "/code.zip");
            File dir = new File(propertyConfig.getFileOutputPath());
            // 判断目录是否存在，不存在就创建目录文件
            if (!dir.exists()) {
                dir.mkdir();
            }
            zip.write(zipFile.toByteArray());
        } catch (Exception e) {
            log.error("文件写入文件失败", e);
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException e) {
                    log.error("file writer close error", e);
                }
            }
        }
    }
}
