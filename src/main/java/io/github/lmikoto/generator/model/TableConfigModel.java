package io.github.lmikoto.generator.model;

import lombok.Data;

/**
 * @author liuyang
 * 2020/7/27 5:59 下午
 */
@Data
public class TableConfigModel {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表别名
     */
    private String tableAlias;

}
