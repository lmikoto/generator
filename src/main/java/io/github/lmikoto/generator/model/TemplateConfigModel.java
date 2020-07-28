package io.github.lmikoto.generator.model;

import lombok.Data;

/**
 * @author liuyang
 * 2020/7/27 5:16 下午
 */
@Data
public class TemplateConfigModel {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 是否选覆盖
     */
    private boolean covered;

    /**
     * 模板路径
     */
    private String templateFile;

    /**
     * 模板生成文件名
     */
    private String targetFileName;

    /**
     * 生成到的目录
     */
    private String targetPath;

    /**
     * 文件编码
     */
    private String encoding;
}
