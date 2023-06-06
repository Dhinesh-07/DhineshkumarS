package com.atdxt.JDBCConnection.Controller;
import com.atdxt.JDBCConnection.Model.bean;
import com.atdxt.JDBCConnection.Model.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {

    private final dao myDao;

    @Autowired
    public AppController(dao myDao) {
        this.myDao = myDao;
    }

    @GetMapping("/api/data")
    public List<bean> showData() {

        return myDao.showData();
    }


    @PostMapping("/data/{name}/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addData(@PathVariable String name, @PathVariable String email) {
        bean data = new bean(null, name, email); // Set id as null
        myDao.addData(data);
    }



/*
    @PostMapping("/data")
    @ResponseStatus(HttpStatus.CREATED)
    public void addData(@RequestBody bean data) {
        myDao.addData(data);
    }

 */



}
