package io.github.lmikoto.generator.model;

import lombok.Data;

import java.util.List;

/**
 * @author liuyang
 * 2020/7/27 4:59 下午
 */
@Data
public class Table {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表驼峰名称
     */
    private String tableCameName;

    /**
     * 表类型
     */
    private String tableType;

    /**
     * 表别名
     */
    private String tableAlias;

    /**
     * 表注释
     */
    private String remark;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * 主键列表
     */
    private Column primaryKey;

    /**
     * 所有列,包括主键
     */
    private List<Column> columns;


    private List<Column> columnsExcludePk;


    private String catalog;


    private String schema;


    private String modelName;
}
