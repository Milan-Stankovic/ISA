package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.Obavestenje;
import com.isa.ISA.service.ObavestenjaService;

@RestController
public class ObavestenjaController {
	@Autowired
    private ObavestenjaService obavestenja;
	
	@RequestMapping(method = RequestMethod.GET, value = "/obavestenja/{username}")
    public List<Obavestenje> getObavestenja(@PathVariable String username){
		return obavestenja.getObavestenja(username);
    }
	@RequestMapping(method = RequestMethod.DELETE, value = "/obavestenja/{id}")
    public Obavestenje deleteObavestenja(@PathVariable long id){
		return obavestenja.deleteObavestenja(id);
    }
}
