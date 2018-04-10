package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.EmailService;
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
	        RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
	        rk.setUserName(kor.getUserName());
	        rk.setPassword(kor.getPassword());
	        rk.setEmail(kor.getEmail());
	        rk.setStatus(StatusNaloga.NERESEN);
	        rk.setBrojTelefona(kor.getBrojTelefona());
	        rk.setGrad(kor.getGrad());
	        rk.setIme(kor.getIme());
	        rk.setPrezime(kor.getPrezime());
	        System.out.println("Kreiran korisnik: " + rk.getUserName());
	        userService.addUser(rk);
	        EmailService es = new EmailService(rk.getEmail());
	        System.out.println(userService.getUser(rk.getUserName()).getUserName());

	        System.out.println("Account with username " + rk.getUserName() + "has been created");
	        return "";
	        	
	        
	    }
}
