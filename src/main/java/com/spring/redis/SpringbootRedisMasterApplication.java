package com.spring.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootRedisMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisMasterApplication.class, args);
    }

}
