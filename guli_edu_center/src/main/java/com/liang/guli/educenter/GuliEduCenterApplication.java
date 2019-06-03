package com.liang.guli.educenter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GuliEduCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(GuliEduCenterApplication.class,args);
    }
}
