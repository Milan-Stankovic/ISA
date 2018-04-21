package com.isa.ISA.controller;

import com.isa.ISA.DTO.TransakcijaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.service.OneClickReservationService;

@RestController
public class OneClickReservationController {

    @Autowired
    private OneClickReservationService ocrSevice;

    @RequestMapping(method = RequestMethod.POST, value = "/oneClick/{id}")
    public void addOneClickReservation(@RequestBody Rezervacija r, @PathVariable Long id){
        ocrSevice.setOneClick(r);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/oneClick/{id}/user/{userId}")
    public TransakcijaDTO updateClickReservation(@PathVariable Long id, @PathVariable Long userId){
        return ocrSevice.reserveSeat(id, userId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/oneClick/{id}")
    public void deleteOneClickReservation(@PathVariable Long id){
        ocrSevice.deleteOneClick(id);
    }

}
