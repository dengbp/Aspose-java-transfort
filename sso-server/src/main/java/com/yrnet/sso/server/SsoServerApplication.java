package com.yrnet.sso.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xuxueli 2018-03-22 23:41:47
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
@MapperScan("com.yrnet.sso.server.dao")
public class SsoServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
	}

}
