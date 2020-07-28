package io.github.lmikoto.generator.impl;

import io.github.lmikoto.generator.DataBase;
import io.github.lmikoto.generator.config.Config;
import io.github.lmikoto.generator.model.CodedType;
import io.github.lmikoto.generator.model.Column;
import io.github.lmikoto.generator.model.DbConfigModel;
import io.github.lmikoto.generator.model.Table;
import io.github.lmikoto.generator.utils.StringUtil;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.*;

/**
 * @author liuyang
 * 2020/7/27 5:04 下午
 */
public class MysqlDataBase implements DataBase {

    private final String SELECT_TABLE_NAME_SQL = "SELECT t.table_catalog,t.table_schema,t.table_name,table_type FROM information_schema.TABLES t where t.table_schema=? and t.table_name like ?";

    private final String SELECT_TABLE_SQL = "SELECT t.table_catalog,t.table_schema,t.table_name,table_type FROM information_schema.TABLES t where t.table_schema=? and t.table_name = ?";

    private final String SELECT_TABLE_COLUMN_SQL = "SELECT t.table_schema,t.table_name,t.column_name,t.column_default, t.is_nullable,t.data_type,t.character_maximum_length,t.numeric_precision,t.numeric_scale,t.column_type,t.column_key, t.column_comment,t.extra FROM information_schema.columns t WHERE t.table_schema = ? AND t.table_name = ?";

    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()){
                Connection conn = null;
                DbConfigModel dbConfigModel = Config.getConfigModel().getDatabase();
                Class.forName(dbConfigModel.getDriverClass());
                conn = DriverManager.getConnection(dbConfigModel.getUrl(), dbConfigModel.getUsername(), dbConfigModel.getPassword());
                connection = conn;
                return conn;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public List<Table> getTables(String tableNamePattern) {
        List<String> tableNames = getTableNames(tableNamePattern);
        List<Table> tables = new ArrayList<Table>();
        for (String tableName : tableNames) {
            tables.add(getTable(tableName));
        }
        return tables;
    }

    @Override
    public List<String> getTableNames(String tableNamePattern) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_TABLE_NAME_SQL);
            ps.setString(1, connection.getCatalog());
            ps.setString(2, tableNamePattern);
            ResultSet rs = ps.executeQuery();
            List<String> tableNames = new ArrayList<String>();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
            return tableNames;
        }catch (Exception e){
            e.printStackTrace();;
        }
        throw new RuntimeException("no table");
    }

    @Override
    public Table getTable(String tableName) {
        Connection connection = getConnection();
        Table table = new Table();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_TABLE_SQL);
            ps.setString(1, connection.getCatalog());
            ps.setString(2, tableName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                table.setTableName(tableName);
                table.setTableCameName(StringUtil.getCamelCaseString(tableName,
                        false));
                table.setEntityName(StringUtil.getCamelCaseString(tableName,
                        true));
                table.setModelName(StringUtil.getCamelCaseString(tableName,
                        true));
                table.setCatalog(rs.getString("table_catalog"));
                table.setSchema(rs.getString("table_schema"));
                table.setTableType(rs.getString("TABLE_TYPE"));
                table.setRemark(getTableComment(tableName));
                table.setColumns(getColumns(tableName));
                table.setPrimaryKey(getPrimaryKeys(tableName).get(0));
                table.setColumnsExcludePk(excludePk(table.getColumns(),table.getPrimaryKey()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    @SneakyThrows
    public List<Column> getColumns(String tableName) {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SELECT_TABLE_COLUMN_SQL);
        ps.setString(1, connection.getCatalog());
        ps.setString(2, tableName);
        ResultSet rs = ps.executeQuery();
        List<Column> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(getColumn(rs));
        }
        return columns;
    }

    @Override
    public List<Column> getPrimaryKeys(String tableName) {
        return getColumns(tableName).stream().filter(item->{
            return item.isPrimaryKey();
        }).collect(Collectors.toList());
    }

    @Override
    public String getTableComment(String tableName) {
        return null;
    }

    private Column getColumn(ResultSet rs) throws SQLException {
        try {
            Column column = new Column();
            String columnName = rs.getString("COLUMN_NAME");
            column.setColumnName(columnName);
            int size = rs.getInt("CHARACTER_MAXIMUM_LENGTH");
            column.setSize(size==0 ? rs.getInt("NUMERIC_PRECISION") : size);
            column.setNullAble("YES".equals(rs.getString("IS_NULLABLE")));
            column.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
            column.setDataType(rs.getString("DATA_TYPE"));
            column.setAutoincrement("auto_increment".equals(rs.getString("EXTRA")));
            column.setRemark(rs.getString("COLUMN_COMMENT"));
            column.setDecimalDigits(rs.getInt("NUMERIC_SCALE"));
            column.setJavaProperty(StringUtil.getCamelCaseString(columnName,
                    false));
            String dataType = column.getDataType().toUpperCase();
            Map<String,String> map = Config.getDataType().get(dataType);
            if(null!=map) {
                column.setJavaType(map.get("javaType"));
                column.setFullJavaType(map.get("fullJavaType"));
            } else {
                column.setJavaType("String");
                column.setFullJavaType("java.lang.String");
            }
            String columnKey = rs.getString("COLUMN_KEY");
            column.setPrimaryKey("PRI".equals(columnKey));
            column.setForeignKey("MUL".equals(columnKey));
            if(column.getColumnName().startsWith("is_")) {
                column.setJavaType("YesNoEnum");
            } else {
                if(StringUtil.isNotEmpty(column.getRemark())) {
                    // 不为空，判断是否为类型注解
                    column.setCodedTypes(remarkToCodedType(column.getRemark()));
                    if(!column.getCodedTypes().isEmpty()){
                        column.setJavaType(StringUtil.getCamelCaseString(columnName,
                                true)+"Enum");
                    }
                }
            }
            return column;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CodedType> remarkToCodedType(String remark) {
        Pattern pattern = compile("(\\d+)->(.+?)\\|([a-zA-Z_0-9]+?)[,)]");
        List<CodedType> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(remark);
        while(matcher.find()) {
            list.add(new CodedType(Integer.parseInt(matcher.group(1)), matcher.group(2),matcher.group(3)));
        }
        return list;
    }

    private List<Column> excludePk(List<Column> columns, Column pk){
        return columns.stream()
                .filter(i -> i.getColumnName() != pk.getColumnName())
                .collect(Collectors.toList());
    }
}
