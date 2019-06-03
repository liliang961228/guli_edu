package com.liang.guli.eduvideoupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
public class GuliEduVideoUploadApplication {

    public static void main(String[] args) {

        SpringApplication.run(GuliEduVideoUploadApplication.class,args);
    }
}
