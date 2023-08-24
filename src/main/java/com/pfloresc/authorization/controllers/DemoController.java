package com.pfloresc.authorization.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo(){
        return "Hola Mundo";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hola Admin";
    }

    @GetMapping("/dba")
    public String dba(){
        return "Hola DBA";
    }

    @GetMapping("/add")
    public String add(){
        return "Hola add nuevo";
    }

    @PostMapping("/add")
    public String add2(){
        return "Hola add nuevo y creado";
    }

}
