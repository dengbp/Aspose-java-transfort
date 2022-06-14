package com.yrnet.pcweb.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author dengbp
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"file:${CONF_DIR}/docview.properties"})
@ConfigurationProperties(prefix = "docview")
public class ViewWebProperties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean autoOpenBrowser = true;
    private String[] autoOpenBrowserEnv = {};
    private SwaggerProperties swagger = new SwaggerProperties();
    private InterfaceProperties interfaceProperties = new InterfaceProperties();
    private MysqlProperties mysqlProperties = new MysqlProperties();
    private boolean openAopLog = true;
    private int maxBatchInsertNum = 1000;
    private String certificate_path;
    private String url_base;

    private String video_parse_url;
    private String file_path;
    private String video_path;
    private Integer share_download_limit;

    private String app_moment_url;


}
