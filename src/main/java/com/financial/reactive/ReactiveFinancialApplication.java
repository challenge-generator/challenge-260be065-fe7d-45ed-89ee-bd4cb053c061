package com.financial.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.financial.reactive")
public class ReactiveFinancialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveFinancialApplication.class, args);
    }
}