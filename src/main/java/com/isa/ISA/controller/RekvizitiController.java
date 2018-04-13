package com.isa.ISA.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(method = RequestMethod.POST, value = "/zvanicni", consumes = {"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public ZvanicanRekvizit addTematske(@RequestBody ZvanicanRekvizit zr){
		return rekvizit.addTematske(zr);
    }
	@RequestMapping(method = RequestMethod.PUT, value = "/zvanicni")
    public ZvanicanRekvizit editTematske(@RequestBody ZvanicanRekvizit zr){
		return rekvizit.editTematske(zr);
    }
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ZvanicanRekvizit addImageToBytes(@RequestBody MultipartFile file){
		String slika = rekvizit.saveMultipartFile(file);
		ZvanicanRekvizit zr = new ZvanicanRekvizit();
		zr.setSlika(slika);
		return zr;
    }
	@RequestMapping(method = RequestMethod.DELETE, value = "/zvanicni/{id}")
    public void deactivateTematske(@PathVariable long id){
		rekvizit.deactivateTematske(id);
    }
}
