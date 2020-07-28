package io.github.lmikoto.generator;

import io.github.lmikoto.generator.config.Config;
import io.github.lmikoto.generator.impl.MysqlDataBase;
import io.github.lmikoto.generator.impl.VelocityEngineImpl;
import io.github.lmikoto.generator.model.Table;
import io.github.lmikoto.generator.model.TableConfigModel;
import io.github.lmikoto.generator.model.TemplateConfigModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyang
 * 2020/7/27 5:19 下午
 */
public class Generator {

    public static void main(String[] args) {
        DataBase dataBase = new MysqlDataBase();
        TemplateEngine templateEngine = new VelocityEngineImpl();
        List<TableConfigModel> tableConfigModelList = Arrays.asList(Config.getConfigModel().getTables());
        List<TemplateConfigModel> templateConfigModelList = Arrays.asList(Config.getConfigModel().getTemplates());

        tableConfigModelList.forEach(tableConfigModel -> {
            List<Table> tableList = dataBase.getTables(tableConfigModel.getTableName());
            tableList.forEach(table -> {
                Map<String,Object> model = new HashMap<>(Config.defaultProps);
                model.put("table",table);
                templateConfigModelList.forEach(templateConfigModel -> {
                    templateEngine.processToFile(model,templateConfigModel);
                });
            });
        });

    }
}
