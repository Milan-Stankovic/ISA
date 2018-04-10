package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.service.RekvizitService;


@RestController
@RequestMapping("/rekviziti")
public class RekvizitiController {
	@Autowired
    private RekvizitService rekvizit;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/zvanicni")
    public List<ZvanicanRekvizit> getTematske(){
		return rekvizit.getTematske();
    }
}
