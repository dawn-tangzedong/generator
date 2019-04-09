package com.rockcandy.modules.mysql.service;

import com.rockcandy.modules.common.domain.ColumnDO;
import com.rockcandy.modules.common.domain.TableDO;
import com.rockcandy.modules.mysql.dao.MysqlGeneratorDao;
import com.rockcandy.common.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author tangzedong.programmer@gmail.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class MysqlGeneratorService {

    @Autowired
    private MysqlGeneratorDao mysqlGeneratorDao;

    /**
     * 生成代码，并以流的形式返回
     *
     * @param tableNames 需要生成的数据库表名数组
     * @return 生成代码后的流
     */
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            TableDO table = mysqlGeneratorDao.queryTable(tableName);
            //查询列信息
            List<ColumnDO> columns = mysqlGeneratorDao.queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }


//    public List<TableDO> queryList(Map<String, Object> map) {
//        return mysqlGeneratorDao.queryList(map);
//    }
//
//    public int queryTotal(Map<String, Object> map) {
//        return mysqlGeneratorDao.queryTotal(map);
//    }
//
//    public TableDO queryTable(String tableName) {
//        return mysqlGeneratorDao.queryTable(tableName);
//    }
//
//    public List<ColumnDO> queryColumns(String tableName) {
//        return mysqlGeneratorDao.queryColumns(tableName);
//    }
}
