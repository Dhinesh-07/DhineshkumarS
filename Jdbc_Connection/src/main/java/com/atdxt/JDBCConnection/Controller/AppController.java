package com.atdxt.JDBCConnection.Controller;
import com.atdxt.JDBCConnection.Model.MyBean;
import com.atdxt.JDBCConnection.Model.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {

    private final MyDao myDao;

    @Autowired
    public AppController(MyDao myDao) {

        this.myDao = myDao;
    }

    @GetMapping("/api")
    public List<MyBean> showData() {

        return myDao.showData();
    }

/*
    @PostMapping("/data/{name}/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addData(@PathVariable String name, @PathVariable String email) {
        bean data = new bean(null, name, email); // Set id as null
        myDao.addData(data);
    }

 */




    @PostMapping("/data")
    @ResponseStatus(HttpStatus.CREATED)
    public void addData(@RequestBody MyBean data) {
        myDao.addData(data);
    }





}
