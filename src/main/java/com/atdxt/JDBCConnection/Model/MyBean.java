package com.atdxt.JDBCConnection.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyBean {

    private Integer id;
    private String name;
    private String email;

    public MyBean() {
    }

    public MyBean(Integer id, String name, String email) {
        this.id=id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters (omitted for brevity)
}