package com.isa.ISA.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/*
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;*/
import javax.servlet.http.HttpServletResponse;

import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

@RestController
public class RegisterController {

	 private RegisterService registerService;

	 @RequestMapping(method = RequestMethod.POST, value = "/api/register/admin") 
	    public String registerA(@RequestBody RegKorDTO admin) throws NoSuchAlgorithmException {
	 		return registerService.registerAdmin(admin);
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register") 
	    public String registerR(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {
		 return registerService.registerRegKor(kor);
	    }

	 @RequestMapping(method = RequestMethod.GET, value = "/api/register/{email}") 
	 	public void register(HttpServletResponse response, @PathVariable String email){
	 		registerService.finishReg(response, email);

	 	}
}
