package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.service.OcenaService;


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
