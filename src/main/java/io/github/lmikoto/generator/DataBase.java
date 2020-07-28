package io.github.lmikoto.generator;

import io.github.lmikoto.generator.model.Column;
import io.github.lmikoto.generator.model.Table;

import java.sql.Connection;
import java.util.List;

/**
 * @author liuyang
 * 2020/7/27 4:57 下午
 */
public interface DataBase {

    /**
     * 获取连接
     * @return
     */
    Connection getConnection();

    /**
     * 关闭连接
     */
    void closeConnection();

    /**
     * 获取表对象
     * @param tableNamePattern
     * @return 表对象实例
     */
    List<Table> getTables(String tableNamePattern);


    /**
     * 获取表名
     * @param tableNamePattern
     * @return
     */
    List<String> getTableNames(String tableNamePattern);

    /**
     * 根据表名获取数据表对象
     *
     * @param tableName 表名
     * @return 表对象实例
     */
    Table getTable(String tableName);

    /**
     * 获取表内的全部列表
     * @param tableName 表名
     * @return 数据列对象
     */
    List<Column> getColumns(String tableName);

    /**
     * 获取表所有主键表
     * @param tableName 表名
     * @return
     */
    List<Column> getPrimaryKeys(String tableName);

    /**
     * 获取表备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    String getTableComment(String tableName);
}
