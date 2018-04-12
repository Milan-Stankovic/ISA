package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isa.ISA.dbModel.PozoristeBioskop;
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
	@RequestMapping(method = RequestMethod.POST, value = "/zvanicni")
    public ZvanicanRekvizit addTematske(@RequestBody ZvanicanRekvizit zr){
		return rekvizit.addTematske(zr);
    }
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ZvanicanRekvizit addImageToBytes(@RequestBody MultipartFile file){
		String slika = rekvizit.saveMultipartFile(file);
		ZvanicanRekvizit zr = new ZvanicanRekvizit();
		zr.setSlika(slika);
		return zr;
    }
}
