package io.github.lmikoto.generator.config;

import io.github.lmikoto.generator.model.ConfigModel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.ho.yaml.Yaml;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang
 * 2020/7/27 5:27 下午
 */
@SuppressWarnings("unchecked")
public class Config {

    public static final String CONFIG_FILE = "config.yml";

    public static final String DATA_TYPE_FILE = "type.yml";

    public static final String CONFIG_PATH = "src/main/resources/" + CONFIG_FILE;

    public static final String DATATYPE_PATH = "src/main/resources/" + DATA_TYPE_FILE ;

    public static final String TEMPLATE_DIR = "src/main/resources/templates/";

    public static Map<String,Object> defaultProps;

    private static ConfigModel configModel;

    private static Map<String, Map<String,String>> dataType;

    static {
        File dataTypeFile = new File(Config.DATATYPE_PATH);
        File configFile = new File(Config.CONFIG_PATH);
        try {
            Config.dataType = Yaml.loadType(dataTypeFile, HashMap.class);
            Config.configModel = Yaml.loadType(configFile, ConfigModel.class);
            defaultProps = new HashMap<>();
            // project info
            defaultProps.put("targetProject", configModel.getTargetProject());
            defaultProps.put("basePackage", configModel.getBasePackage());
            defaultProps.put("moduleName", configModel.getModuleName());
            defaultProps.put("author",configModel.getAuthor());

            // lib
            defaultProps.put("dateFormat",new DateFormatUtils());

            // other
            defaultProps.put("now",new Date());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ConfigModel getConfigModel(){
        return configModel;
    }

    public static Map<String, Map<String,String>> getDataType(){
        return dataType;
    }
}