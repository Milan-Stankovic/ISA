package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class RegisterController {
	 @Autowired
	    private UserService userService;
	 @Autowired
	    private AdminService adminService;
	 
	 public RegisterController() {
	    }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register") 
	    public String register(@RequestBody RegistrovaniKorisnik kor){
	        Korisnik k;
	        RegistrovaniKorisnik reg = userService.getUser(kor.getUserName());
	        Admin adm = adminService.getAdmin(kor.getUserName());
	        if(reg!=null || adm!=null){
	       
	            return "Username is already taken.";
	        }
	        RegistrovaniKorisnik korEmail = userService.findByEmail(kor.getEmail());
	        Admin adminEmail = adminService.getAdminByEmail(kor.getEmail());
	        if(korEmail!=null || adminEmail!=null){
	       
	            return "Email is already taken.";
	        }
	        
	        RegistrovaniKorisnik novi = new RegistrovaniKorisnik();
	        novi.setUserName(kor.getUserName());
	        novi.setPassword(kor.getPassword());
	        novi.setEmail(kor.getEmail());
	        novi.setBrojTelefona(kor.getBrojTelefona());
	        novi.setGrad(kor.getGrad());
	        novi.setIme(kor.getIme());
	        novi.setPrezime(kor.getPrezime());
	        novi.setStatus(StatusNaloga.NERESEN);
	        userService.addUser(novi);
	        return "Account with username " + novi.getUserName() + "has been created. Finish your registration with link we sent you on email.";
	        	
	        
	    }
}
