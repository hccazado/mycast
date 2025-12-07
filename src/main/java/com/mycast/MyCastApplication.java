package com.mycast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MyCastApplication {

    private static final Logger log = LoggerFactory.getLogger(MyCastApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyCastApplication.class, args);
    }
}
