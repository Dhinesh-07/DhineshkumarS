package com.atdxt.JDBCConnection;

import com.atdxt.JDBCConnection.Model.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcConnectionApplication implements CommandLineRunner {

	private final dao myDao;

	@Autowired
	public JdbcConnectionApplication(dao myDao) {

		this.myDao = myDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(JdbcConnectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		myDao.showData();
	}
}
