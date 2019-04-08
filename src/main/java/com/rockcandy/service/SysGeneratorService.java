package com.rockcandy.service;

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

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return mysqlGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return mysqlGeneratorDao.queryTotal(map);
    }

    public Map<String, String> queryTable(String tableName) {
        return mysqlGeneratorDao.queryTable(tableName);
    }

    public List<Map<String, Object>> queryColumns(String tableName) {
        return mysqlGeneratorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, Object>> columns = queryColumns(tableName);
            //生成代码
//            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
