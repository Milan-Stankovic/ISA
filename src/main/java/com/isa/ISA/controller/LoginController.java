package com.isa.ISA.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import com.isa.ISA.DTO.RegKorDTO;/*
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;*/
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;
import com.isa.ISA.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;*/
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.UserService;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/login")
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://localhost:8096/login.html");
    }
    // Mozes li ubaciti dodatnu proveru ako je admin da li je promenio password ?
    @RequestMapping(method = RequestMethod.POST, value = "/api/login") 
    public Korisnik login(@RequestBody RegKorDTO credentials) throws NoSuchAlgorithmException {
    	return loginService.login(credentials);

    }

}
