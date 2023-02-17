package com.example.savefile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SaveFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveFileApplication.class, args);
    }

}
