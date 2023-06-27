package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class BoxApplication {

    private static final Logger logger = LoggerFactory.getLogger(BoxApplication.class);

    public static void main(String[] args) {
        try {
            logger.info("all correct statement");

            int[] array = {1, 2, 3};
            int value = array[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("An error occurred: " + e.getMessage());
            logger.error("error bound", e);
        }

        SpringApplication.run(BoxApplication.class, args);
    }
}
