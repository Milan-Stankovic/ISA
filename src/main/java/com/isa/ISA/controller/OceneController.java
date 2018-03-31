package com.isa.ISA.controller;

import com.isa.ISA.service.OcenaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;


@RestController
public class OceneController {

    @Autowired
    private OcenaService ocenaService;


    @RequestMapping(method = RequestMethod.PUT, value = "/profile/{id}/pozivi/{id2}/oceniA")
    public void oceniA(@PathVariable Long id, @PathVariable Long id2, @RequestBody int ocenaAmbijenta){
        ocenaService.oceniA(id, ocenaAmbijenta);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/{id}/pozivi/{id}/oceniD")
    public void oceniD(@PathVariable Long id, @RequestBody int ocenaDogadjaja){
        ocenaService.oceniD(id, ocenaDogadjaja);
    }

}
