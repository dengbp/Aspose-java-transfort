package com.yrnet.registrycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author dengbp
 * @ClassName RegistryCenterApplication
 * @Description TODO
 * @date 4/9/22 9:44 PM
 */

@SpringBootApplication
@EnableEurekaServer
public class RegistryCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryCenterApplication.class, args);
    }
}
