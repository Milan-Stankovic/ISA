package com.isa.ISA.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/api/hi")
    public String hi(){
        return "Hello ISA";
    }
}
