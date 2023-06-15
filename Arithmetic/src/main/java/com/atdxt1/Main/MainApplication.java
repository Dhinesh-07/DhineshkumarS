package com.atdxt1.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);

		int num1 = 10;
		int num2 = 5;


		int Addition = num1 + num2;
		System.out.println("Sum: " + Addition);


		int Subtraction = num1 - num2;
		System.out.println("Difference: " + Subtraction);


		int Multiplication = num1 * num2;
		System.out.println("Product: " + Multiplication);


		int Division = num1 / num2;
		System.out.println("Quotient: " + Division);
	}

}
