package io.github.lmikoto.generator.model;

import lombok.Data;

/**
 * @author liuyang
 * 2020/7/27 5:59 下午
 */
@Data
public class DbConfigModel {

    /**
     * jdbc驱动
     */
    private String driverClass;

    /**
     * url
     */
    private String url;

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
