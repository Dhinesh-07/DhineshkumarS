package com.atdxt.JDBCConnection;

import com.atdxt.JDBCConnection.Model.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class JdbcConnectionApplication implements CommandLineRunner {

	private final MyDao myDao;

	@Autowired
	public JdbcConnectionApplication(MyDao myDao) {

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
