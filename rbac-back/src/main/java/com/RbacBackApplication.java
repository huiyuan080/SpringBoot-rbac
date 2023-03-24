package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huiyuan080
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.mapper"})
public class RbacBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacBackApplication.class, args);
    }

}
