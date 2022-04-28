package com.yrnet.baseadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BaseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseAdminApplication.class, args);
    }

}
