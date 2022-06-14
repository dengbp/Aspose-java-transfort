package com.yrnet.pcweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

@EnableAsync
@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
@MapperScan("com.yrnet.pcweb.**.mapper")
public class PcWebApplication {

    public static void main(String[] args) {


        SpringApplication.run(PcWebApplication.class, args);
    }

}
