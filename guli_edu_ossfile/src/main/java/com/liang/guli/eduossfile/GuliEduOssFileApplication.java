package com.liang.guli.eduossfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GuliEduOssFileApplication {

    public static void main(String[] args) {

        SpringApplication.run(GuliEduOssFileApplication.class,args);
    }
}

