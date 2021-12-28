package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = "com")
@EnableRetry
public class AWSCognitoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AWSCognitoApplication.class, args);
    }
}