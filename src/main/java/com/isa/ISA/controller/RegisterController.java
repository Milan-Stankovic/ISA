package com.isa.ISA.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.isa.ISA.DTO.RegKorDTO;
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
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register/admin") 
	    public void registerA(@RequestBody RegKorDTO admin){
		 Admin a = adminService.getAdmin(admin.getUserName());
		 System.out.println("Status pre "+a.getStatus());
		 a.setPassword(admin.getPassword());
		 adminService.addAdmin(a);
		 a = adminService.getAdmin(a.getUserName());
		 System.out.println("Status posle "+a.getStatus());
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register") 
	    public String registerR(@RequestBody RegKorDTO kor){

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
	 @RequestMapping(method = RequestMethod.GET, value = "/api/register/{email}") 
	 	public void register(HttpServletResponse response, @PathVariable String email){
		 	String mail = email + ".com";
		 	RegistrovaniKorisnik reg = userService.findByEmail(mail);
	        Admin adm = adminService.getAdminByEmail(mail);
	        Korisnik k = (reg != null) ? reg: adm;
	 		if(k!=null){
	 			k.setStatus(StatusNaloga.AKTIVAN);
	 			userService.addUser((RegistrovaniKorisnik)k);

	 		}
	 		try {
	 			RegistrovaniKorisnik re = userService.findByEmail(mail);
	 			System.out.println(mail);
	 			System.out.println(re);
	 			System.out.println("STATUS " + re.getStatus());
				response.sendRedirect("http://localhost:8096/#!/login");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
}
