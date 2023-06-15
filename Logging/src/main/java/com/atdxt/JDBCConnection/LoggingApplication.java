package com.atdxt.JDBCConnection;


import com.atdxt.JDBCConnection.Controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoggingApplication {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public static void main(String[] args) {
		try {
			logger.info("all correct staement");

			int[] array = {1, 2, 3};
			int value = array[5];
		}  catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("An error occurred: " + e.getMessage());
			logger.error("error bound",e);
		}

		SpringApplication.run(LoggingApplication.class, args);
	}

}

