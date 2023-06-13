package com.atdxt.JDBCConnection;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcConnectionApplication {

	public static void main(String[] args) {
		try {
			// Intentionally causing an error by accessing an invalid index
			int[] array = {1, 2, 3};
			int value = array[5];
		} catch (ArrayIndexOutOfBoundsException e) {
			// Handling the error
			System.out.println("An error occurred: " + e.getMessage());
		}
		SpringApplication.run(JdbcConnectionApplication.class, args);
	}

}

