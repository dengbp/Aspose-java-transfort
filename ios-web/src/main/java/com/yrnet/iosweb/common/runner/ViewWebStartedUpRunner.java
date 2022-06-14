package com.yrnet.iosweb.common.runner;

import com.yrnet.iosweb.common.properties.ViewWebProperties;
import com.yrnet.iosweb.monitor.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

/**
 * @author dengbp
 */
@Slf4j
@Component
public class ViewWebStartedUpRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private ViewWebProperties yinXXProperties;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean testRedis = true;
        if (testRedis) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = yinXXProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)){
                url += contextPath;
            }
            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }
            log.info("app-web-api 系统启动完毕，地址：{}", url);
            boolean auto = yinXXProperties.isAutoOpenBrowser();
            String[] autoEnv = yinXXProperties.getAutoOpenBrowserEnv();
            if (auto && ArrayUtils.contains(autoEnv, active)) {
                String os = System.getProperty("os.name");
                if (StringUtils.containsIgnoreCase(os, "windows")) {
                    Runtime.getRuntime().exec("cmd  /c  start " + url);
                }
            }
        }
    }
}
