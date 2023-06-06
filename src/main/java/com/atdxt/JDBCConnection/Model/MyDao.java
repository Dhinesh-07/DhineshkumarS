package com.atdxt.JDBCConnection.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }




    public void addData(MyBean data) {
        String query = "INSERT INTO demo (name, email) VALUES (?, ?)";
        jdbcTemplate.update(query, data.getName(), data.getEmail());
    }


    public List<MyBean> showData() {
        String query = "SELECT * FROM demo";
        List<MyBean> result = jdbcTemplate.query(query, (rs, rowNum) -> {
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new MyBean(id, name, email);
        });

        return result;
    }
}
