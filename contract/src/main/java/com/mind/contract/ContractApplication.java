package com.mind.contract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mind.contract.dao")
public class ContractApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractApplication.class, args);
    }

}
