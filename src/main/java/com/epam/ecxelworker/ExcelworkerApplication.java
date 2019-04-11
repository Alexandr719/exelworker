package com.epam.ecxelworker;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class ExcelworkerApplication implements CommandLineRunner {

    @Autowired
    Application app;


    public static void main(String[] args) {
        SpringApplication.run(ExcelworkerApplication.class, args);

    }

    @Override
    public void run(String... args) {
        app.runConsoleApplication();
    }
}
