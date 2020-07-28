package io.github.lmikoto.generator.impl;

import io.github.lmikoto.generator.TemplateEngine;
import io.github.lmikoto.generator.config.Config;
import io.github.lmikoto.generator.model.TemplateConfigModel;
import lombok.SneakyThrows;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author liuyang
 * 2020/7/27 5:53 下午
 */
public class VelocityEngineImpl implements TemplateEngine {

    static {
        Velocity.init();
    }

    @SneakyThrows
    @Override
    public void processToFile(Map<String, Object> ctx, TemplateConfigModel config) {
        String templatePath = Config.TEMPLATE_DIR + config.getTemplateFile();
        VelocityContext context = new VelocityContext();
        copyCtx(ctx,context);
        StringWriter fileName = new StringWriter();
        StringWriter pathName = new StringWriter();
        Velocity.evaluate(context,fileName,"",config.getTargetFileName());
        Velocity.evaluate(context,pathName,"",config.getTargetPath());
        String path = pathName.toString().replaceAll("\\.","/");
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(path + fileName));
        Velocity.mergeTemplate(templatePath,config.getEncoding(),context,out);
        out.flush();
        out.close();

    }

    void copyCtx(Map<String, Object> ctx,VelocityContext context){
        for(Map.Entry entry: ctx.entrySet()){
            context.put(entry.getKey().toString(),entry.getValue());
        }
    }
}
