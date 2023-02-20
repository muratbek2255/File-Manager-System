package com.example.savefile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SaveFileApplication {

    @Value("${custom.basedir}")
    private String stringValue;


    public static void main(String[] args) {
        SpringApplication.run(SaveFileApplication.class, args);
    }

    @PostConstruct
    public void printCategory(){
        System.out.println("Print environment values");
        System.out.println(stringValue);
    }
}
