package com.hmall;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan("com.hmall.mapper")
@SpringBootApplication
public class HMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(HMallApplication.class, args);
        log.info("启动成功");
    }
}