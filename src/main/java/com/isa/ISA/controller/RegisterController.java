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
import com.isa.ISA.service.EncryptionService;
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

	@Autowired
	private EncryptionService encService;

	 public RegisterController() {
	    }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register/admin") 
	    public String registerA(@RequestBody RegKorDTO admin) throws NoSuchAlgorithmException {
		 Admin a = adminService.getAdmin(admin.getUserName());
		if(a==null)
			return "No user with that data.";
		if(!a.getStatus().toString().equals("NERESEN"))
			return "This account is already active.";
		 byte[] salt = encService.getNextSalt();
		 byte[] newPass = encService.makeDigest(admin.getPassword(), salt);
		 String pass = Arrays.toString(newPass);
		 Encryption e = encService.getEncrUser(a.getId());
		 e.setSalt(salt);
		 e.setEncryptedPass(newPass);
		 encService.addEncr(e);
		 a.setPassword(pass);
		 a.setStatus(StatusNaloga.AKTIVAN);
		 if(admin.getIme()!=null && !admin.getIme().equals("")) a.setIme(admin.getIme());
		 if(admin.getPrezime()!=null && !admin.getPrezime().equals("")) a.setPrezime(admin.getPrezime());
		 if(admin.getGrad()!=null && !admin.getGrad().equals("")) a.setGrad(admin.getGrad());
		 if(admin.getEmail()!=null && !admin.getEmail().equals("")) a.setEmail(admin.getEmail());
		 if(admin.getBrojTelefona()!=null && !admin.getBrojTelefona().equals("")) a.setBrojTelefona(admin.getBrojTelefona());
		 adminService.addAdmin(a);
		 a = adminService.getAdmin(a.getUserName());
		 return "";
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/api/register") 
	    public String registerR(@RequestBody RegKorDTO kor) throws NoSuchAlgorithmException {

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

			 byte[] salt = encService.getNextSalt();
			 byte[] newPass = encService.makeDigest(kor.getPassword(), salt);
			 String pass = Arrays.toString(newPass);
			 Encryption e = new Encryption();
			 e.setSalt(salt);
			 e.setEncryptedPass(newPass);
			 e.setKorisnikID(rk.getId());
			 encService.addEncr(e);
			 rk.setPassword(pass);

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
				/*final String salt = KeyGenerators.string().generateKey();

				TextEncryptor encryptor = Encryptors.text("admin", salt);
				System.out.println("Salt: \"" + salt + "\"");

				String textToEncrypt = k.getPassword();
				System.out.println("Original text: \"" + textToEncrypt + "\"");

				String encryptedText = encryptor.encrypt(textToEncrypt);
				System.out.println("Encrypted text: \"" + encryptedText + "\"");

				Encryption e = new Encryption(encryptedText, salt, k.getId());
				encService.addEncr(e);
				k.setPassword(encryptedText);*/
/*
				// Could reuse encryptor but wanted to show reconstructing TextEncryptor
				TextEncryptor decryptor = Encryptors.text(password, salt);
				String decryptedText = decryptor.decrypt(encryptedText);
				System.out.println("Decrypted text: \"" + decryptedText + "\"");

				if(textToEncrypt.equals(decryptedText)) {
					System.out.println("Success: decrypted text matches");
				} else {
					System.out.println("Failed: decrypted text does not match");
				}*/
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
