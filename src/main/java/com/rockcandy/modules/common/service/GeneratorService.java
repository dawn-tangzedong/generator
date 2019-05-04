package com.rockcandy.modules.common.service;

import com.rockcandy.common.config.PropertiesConfig;
import com.rockcandy.common.utils.GenUtils;
import com.rockcandy.common.utils.NameUtils;
import com.rockcandy.modules.common.dao.GeneratorDao;
import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipOutputStream;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote Code Generator Service
 * @since 2019/4/9 16:44
 */
@Service
@Slf4j
public abstract class GeneratorService<Dao extends GeneratorDao> {

    @Autowired
    protected Dao dao;

    @Autowired
    protected PropertiesConfig defaultConfig;

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
        for (String tableName : propertyConfig.getGeneratorTableName()) {
            //查询表信息
            TableDO table = dao.queryTable(tableName);
            if (table == null) {
                break;
            }
            //查询列信息
            List<ColumnDO> columns = dao.queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, disposeTableInfo(table,columns), zip);
        }
        IOUtils.closeQuietly(zip);
        // 把文件流写入磁盘中
        writeDisk(outputStream);
    }

    /**
     * 处理数据库表、列信息
     *
     * @param table   数据库表
     * @param columns 数据库列集合
     * @return 数据库表结构是否存在bigDecimal数据类型
     */
    private AtomicBoolean disposeTableInfo(TableDO table, List<ColumnDO> columns) {
        AtomicBoolean hasBigDecimal = new AtomicBoolean(false);
        // class名称转化驼峰以及变量名
        table.setUpperClassName(NameUtils.convertName(table.getTableName(), StringUtils.split(defaultConfig.getTablePrefix(), ",")));
        table.setLowerClassName(StringUtils.uncapitalize(table.getUpperClassName()));
        table.setPathName(table.getLowerClassName().toLowerCase());
        // 列信息处理
        disposeColumns(table, columns, hasBigDecimal);
        return hasBigDecimal;
    }

    /**
     * 处理数据库列信息
     *
     * @param table         数据库表
     * @param columns       数据库列集合
     * @param hasBigDecimal 是否有bigDecimal数据格式
     * @return 数据库表结构是否存在bigDecimal数据类型
     */
    protected abstract void disposeColumns(TableDO table, List<ColumnDO> columns, AtomicBoolean hasBigDecimal);

    /**
     * 文件流写入磁盘中
     *
     * @param zipFile 输出流
     */
    private void writeDisk(ByteArrayOutputStream zipFile) {
        FileOutputStream zip = null;
        try {
            File dir = new File(propertyConfig.getFileOutputPath());
            // 判断目录是否存在，不存在就创建目录文件
            if (!dir.exists()) {
                dir.mkdir();
            }
            zip = new FileOutputStream(propertyConfig.getFileOutputPath() + "/code.zip");
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
