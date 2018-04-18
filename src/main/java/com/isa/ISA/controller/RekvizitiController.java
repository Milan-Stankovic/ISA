package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isa.ISA.DTO.PolovanRekvDTO;
import com.isa.ISA.DTO.PonudaDTO;
import com.isa.ISA.DTO.RekvizitDTO;
import com.isa.ISA.dbModel.PolovanRekvizit;
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/polovni/{userID}")
    public List<PolovanRekvizit> getPolovan(@PathVariable long userID){
		return rekvizit.getPolovan(userID);
    }
	@RequestMapping(method = RequestMethod.POST, value = "/polovni")
    public PolovanRekvizit addPolovan(@RequestBody PolovanRekvDTO rekDTO){
		return rekvizit.addPolovan(rekDTO);
    }
	@RequestMapping(method = RequestMethod.POST, value = "/polovni/ponuda")
    public void ponudiPolovne(@RequestBody PonudaDTO p){
		 rekvizit.ponudiPolovne(p);
    }
	@RequestMapping(method = RequestMethod.PUT, value = "/polovni/ponuda")
    public void updatePonude(@RequestBody PonudaDTO p){
		 rekvizit.updatePonude(p);
    }
	@RequestMapping(method = RequestMethod.GET, value = "/mojiOglasi/{userID}")
    public List<PolovanRekvizit> getMojiOglasi(@PathVariable long userID){
		return rekvizit.getMojiOglasi(userID);
    }

	@RequestMapping(method = RequestMethod.GET, value = "/mojePonude/{username}")
    public List<PolovanRekvizit> getMojePonude(@PathVariable String username){
		return rekvizit.getMojePonude(username);
    }
}
