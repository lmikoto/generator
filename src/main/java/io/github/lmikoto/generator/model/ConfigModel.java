package io.github.lmikoto.generator.model;

import lombok.Data;

/**
 * @author liuyang
 * 2020/7/27 5:29 下午
 */
@Data
public class ConfigModel {

    /**
     * 数据库配置模型
     */
    private DbConfigModel database;

    /**
     * 基础包名
     */
    private String basePackage;

    /**
     * 生成代码根目录
     */
    private String targetProject;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 作者
     */
    private String author;

    /**
     * 表配置
     */
    private TableConfigModel [] tables;

    /**
     * 模板配置
     */
    private TemplateConfigModel [] templates;
}
