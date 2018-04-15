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

import com.isa.ISA.DTO.RekvizitDTO;
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
    public ZvanicanRekvizit addTematske(@RequestBody RekvizitDTO rekDTO){
		return rekvizit.addTematske(rekDTO);
    }
	@RequestMapping(method = RequestMethod.PUT, value = "/zvanicni/{id}")
    public ZvanicanRekvizit editTematske(@RequestBody RekvizitDTO rekDTO, @PathVariable long id){
		return rekvizit.editTematske(rekDTO, id);
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

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/rezervisi/{userID}")
    public ZvanicanRekvizit rezervisiTematske(@PathVariable long id, @PathVariable long userID){
		return rekvizit.rezervisiTematske(id, userID);
    }
}
