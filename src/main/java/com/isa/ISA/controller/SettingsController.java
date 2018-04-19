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
	    private UserService userService;
	 @Autowired
	    private AdminService adminService;
	 @Autowired
	 	private EncryptionService encService;
	 
	public SettingsController(){
		
	}
	
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/reg") 
	    public String save(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {
		 	Korisnik k;
	        RegistrovaniKorisnik reg = userService.getUser(kor.getUserName());
	        
	        if(reg==null)
	            return "Error, no such user";
	        
	        else{
	            k = reg;
	        }

	        RegistrovaniKorisnik korEmail = userService.findByEmail(kor.getEmail());
	        Admin adminEmail = adminService.getAdminByEmail(kor.getEmail());
	        if((korEmail!=null && korEmail.getUserName()!=k.getUserName() )|| adminEmail!=null){
	       
	            return "Email is already taken.";
	        }

	        if(kor.getEmail()!=null && !kor.getEmail().equals("")) k.setEmail(kor.getEmail());
		 	if(kor.getIme()!=null && !kor.getIme().equals("")) k.setIme(kor.getIme());
			if(kor.getPrezime()!=null && !kor.getPrezime().equals("")) k.setPrezime(kor.getPrezime());
			if(kor.getBrojTelefona()!=null && !kor.getBrojTelefona().equals("")) k.setBrojTelefona(kor.getBrojTelefona());
			if(kor.getGrad()!=null && !kor.getGrad().equals(""))  k.setGrad(kor.getGrad());
		 	if(kor.getPassword()!=null && !kor.getPassword().equals("")){
			 Encryption e = encService.getEncrUser(k.getId());
			 System.out.println("enc pass: " + Arrays.toString(e.getEncryptedPass()));
			 System.out.println("got pass: " + kor.getPassword());

			 if(Arrays.toString(e.getEncryptedPass()).equals(kor.getPassword())){
				 System.out.println("NIJe menjao pass");
			 }else{
				 System.out.println("JEST menjao pass");
				 byte[] salt = encService.getNextSalt();
				 byte[] newPass = encService.makeDigest(kor.getPassword(), salt);
				 String pass = Arrays.toString(newPass);
				 System.out.println(pass);
				 e.setSalt(salt);
				 e.setEncryptedPass(newPass);
				 encService.addEncr(e);
				 k.setPassword(pass);
			 }
		 }

	        userService.addUser((RegistrovaniKorisnik) k);
	        return "";
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/admin") 
	    public String save2(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {

		 	Korisnik k;
	        Admin adm = adminService.getAdmin(kor.getUserName());
	        if(adm==null)
	            return "Error, no such user";
	        
	        else{
	            k = adm;
	        }
	        
	        RegistrovaniKorisnik korEmail = userService.findByEmail(kor.getEmail());
	        Admin adminEmail = adminService.getAdminByEmail(kor.getEmail());
	        if( (adminEmail!=null && adminEmail.getUserName()!=k.getUserName() )|| korEmail!=null){
	       
	            return "Email is already taken.";
	        }
		 if(kor.getEmail()!=null && !kor.getEmail().equals("")) k.setEmail(kor.getEmail());
		 if(kor.getIme()!=null && !kor.getIme().equals("")) k.setIme(kor.getIme());
		 if(kor.getPrezime()!=null && !kor.getPrezime().equals("")) k.setPrezime(kor.getPrezime());
		 if(kor.getBrojTelefona()!=null && !kor.getBrojTelefona().equals("")) k.setBrojTelefona(kor.getBrojTelefona());
		 if(kor.getGrad()!=null && !kor.getGrad().equals(""))  k.setGrad(kor.getGrad());
		 if(kor.getPassword()!=null && !kor.getPassword().equals("")){
			 Encryption e = encService.getEncrUser(k.getId());
			 System.out.println("enc pass: " + Arrays.toString(e.getEncryptedPass()));
			 System.out.println("got pass: " + kor.getPassword());

			 if(Arrays.toString(e.getEncryptedPass()).equals(kor.getPassword())){
				 System.out.println("NIJe menjao pass");
			 }else{
				 System.out.println("JEST menjao pass");
				 byte[] salt = encService.getNextSalt();
				 byte[] newPass = encService.makeDigest(kor.getPassword(), salt);
				 String pass = Arrays.toString(newPass);
				 System.out.println(pass);
				 e.setSalt(salt);
				 e.setEncryptedPass(newPass);
				 encService.addEncr(e);
				 k.setPassword(pass);
			 }
		 }


		 adminService.addAdmin((Admin) k);
	        return "";
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/settings/search") 
	    public List<RegistrovaniKorisnik> search(@RequestBody String search){
		 List<RegistrovaniKorisnik> ret = new ArrayList<>();	
		 if(search==null || search=="")
			 return ret;	
		 System.out.println(search);
		 String ime = (search.split("\\.")[0].equals("undefined") || search.split("\\.")[0].equals("")) ?  "" : search.split("\\.")[0];
		 String prezime="";
		 if (!search.substring(search.length()-1).equals(".")) {
			 prezime = (search.split("\\.")[1].equals("undefined") || search.split("\\.")[1].equals("")) ? "" : search.split("\\.")[1];
		 }
			 System.out.println("Dobio ime: " + ime + " " + prezime);
		 if(ime.equals(""))
			 return userService.searchPrezime(prezime);
		 else if(prezime.equals(""))
			 return userService.searchIme(ime);
		 else

			 return userService.searchImePrezime(ime,prezime);
	
	 }
}
