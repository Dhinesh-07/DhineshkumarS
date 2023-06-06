package com.atdxt.JDBCConnection.Controller;
import com.atdxt.JDBCConnection.Model.bean;
import com.atdxt.JDBCConnection.Model.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    private final dao myDao;

    @Autowired
    public AppController(dao myDao) {
        this.myDao = myDao;
    }

    @GetMapping("/api/data")
    public ResponseEntity<List<bean>> fetchData() {
        List<bean> data = myDao.fetchData();
        return ResponseEntity.ok(data);
    }
}
