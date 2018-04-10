package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	    private UserService userService;
	 @Autowired
	    private AdminService adminService;
	 
	public SettingsController(){
		
	}
	
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/reg") 
	    public String save(@RequestBody RegistrovaniKorisnik kor){
		 	Korisnik k;
	        RegistrovaniKorisnik reg = userService.getUser(kor.getUserName());
	        
	        if(reg==null)
	            return "Error, no such user";
	        
	        else{
	            k = reg;
	        }
	        
	        RegistrovaniKorisnik korEmail = userService.findByEmail(kor.getEmail());
	        Admin adminEmail = adminService.getAdminByEmail(kor.getEmail());
	        if(korEmail!=null || adminEmail!=null){
	       
	            return "Email is already taken.";
	        }
	        
	      
	        userService.addUser((RegistrovaniKorisnik) k);
	        return "";
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/admin") 
	    public String save(@RequestBody Admin kor){
		 	Korisnik k;
	        Admin adm = adminService.getAdmin(kor.getUserName());
	        if(adm==null)
	            return "Error, no such user";
	        
	        else{
	            k = adm;
	        }
	        
	        RegistrovaniKorisnik korEmail = userService.findByEmail(kor.getEmail());
	        Admin adminEmail = adminService.getAdminByEmail(kor.getEmail());
	        if(korEmail!=null || adminEmail!=null){
	       
	            return "Email is already taken.";
	        }
	        
	        adminService.addAdmin((Admin) k);
	        return "";
	 }
}
