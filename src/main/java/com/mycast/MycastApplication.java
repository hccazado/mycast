package com.mycast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MycastApplication {

    private static final Logger log = LoggerFactory.getLogger(MycastApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MycastApplication.class, args);
    }
}
