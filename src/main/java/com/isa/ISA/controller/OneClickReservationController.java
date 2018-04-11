package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.service.OneClickReservationService;

@Controller
public class OneClickReservationController {

    @Autowired
    private OneClickReservationService ocrSevice;

    @RequestMapping(method = RequestMethod.POST, value = "/oneClick/{id}")
    public void addOneClickReservation(@RequestBody Rezervacija r, @PathVariable Long id){
        ocrSevice.setOneClick(r);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/oneClick/{id}")
    public void updateClickReservation(@RequestBody Rezervacija r, @PathVariable Long id){
        ocrSevice.updateOneClick(r);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/oneClick/{id}")
    public void deleteOneClickReservation(@PathVariable Long id){
        ocrSevice.deleteOneClick(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oneClickReserve/{id}")
    public void reserve(@RequestBody Rezervacija r, @PathVariable Long id){
        ocrSevice.reserveSeat(r);
    }
}
