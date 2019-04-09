package com.rockcandy.service;

import com.rockcandy.domain.ColumnDO;
import com.rockcandy.domain.TableDO;
import com.rockcandy.modules.mysql.dao.MysqlGeneratorDao;
import com.rockcandy.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author tangzedong.programmer@gmail.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class SysGeneratorService {
    @Autowired
    private MysqlGeneratorDao mysqlGeneratorDao;

    public List<TableDO> queryList(Map<String, Object> map) {
        return mysqlGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return mysqlGeneratorDao.queryTotal(map);
    }

    public TableDO queryTable(String tableName) {
        return mysqlGeneratorDao.queryTable(tableName);
    }

    public List<ColumnDO> queryColumns(String tableName) {
        return mysqlGeneratorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            TableDO table = queryTable(tableName);
            //查询列信息
            List<ColumnDO> columns = queryColumns(tableName);
            //生成代码
//            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
