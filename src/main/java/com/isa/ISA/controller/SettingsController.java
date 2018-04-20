package com.isa.ISA.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.isa.ISA.DTO.AdminDTO;
import com.isa.ISA.DTO.RegKorDTO;
/*import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;*/
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;
import com.isa.ISA.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;*/
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.UserService;

@RestController
public class SettingsController {

	 @Autowired
	    private SettingsService settingsService;
	 
	public SettingsController(){
		
	}
	
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/reg") 
	    public String save(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {
		 	return settingsService.saveReg(kor);

	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/admin") 
	    public String save2(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {
			return settingsService.saveAdm(kor);
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/search") 
	    public List<RegistrovaniKorisnik> search(@RequestBody String search){
		 return settingsService.search(search);
	 }
}
