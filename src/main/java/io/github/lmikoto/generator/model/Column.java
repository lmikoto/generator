package io.github.lmikoto.generator.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * 2020/7/27 5:03 下午
 */
@Data
public class Column {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    /**
     * 是否为外键
     */
    private boolean foreignKey;
    /**
     * 列长度
     */
    private int size;
    /**
     * 小数点位数
     */
    private int decimalDigits;
    /**
     * 是否为空
     */
    private boolean nullAble;
    /**
     * 是否默认自增
     */
    private boolean autoincrement;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 字段注释
     */
    private String remark;
    /**
     * 数据库类型
     */
    private String dataType;

    /**
     * 实体类对应属性
     */
    private String javaProperty;
    /**
     * set方法名称
     */
    private String setMethodName;
    /**
     * get方法名称
     */
    private String getMethodName;
    /**
     * java数据类型
     */
    private String javaType;
    /**
     * 数据类型全
     */
    private String fullJavaType;
    /**
     * 枚举集合
     */
    private List<CodedType> codedTypes = new ArrayList<>();
}
