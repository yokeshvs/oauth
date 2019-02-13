package com.fl.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(scanBasePackages = {"com.fl.service.controller", "com.fl.service.repository", "com.fl.service.config", "com.fl.service.security", "com.fl.service.handler"})
@SpringBootApplication
//@ComponentScan("com.fl.service")
@MapperScan("com.fl.service.mapper")
public class OauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }
}
