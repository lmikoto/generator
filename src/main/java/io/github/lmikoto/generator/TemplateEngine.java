package io.github.lmikoto.generator;

import io.github.lmikoto.generator.model.TemplateConfigModel;

import java.util.Map;

/**
 * @author liuyang
 * 2020/7/27 5:12 下午
 */
public interface TemplateEngine {

    /**
     * 生成文件
     * @param ctx
     * @param config
     */
    void processToFile(Map<String, Object> ctx, TemplateConfigModel config);
}
