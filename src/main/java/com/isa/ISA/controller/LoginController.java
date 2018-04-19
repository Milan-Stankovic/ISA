package com.isa.ISA.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.isa.ISA.DTO.RegKorDTO;/*
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.service.EncryptionService;*/
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
    private UserService userService;
    @Autowired
    private AdminService adminService;

    /*@Autowired
    private EncryptionService encService;
*/
    public LoginController() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/login")
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://localhost:8096/login.html");
    }
    // Mozes li ubaciti dodatnu proveru ako je admin da li je promenio password ?
    @RequestMapping(method = RequestMethod.POST, value = "/api/login") 
    public Korisnik login(@RequestBody RegKorDTO credentials){
    	/*String username = credentials.split("\\.")[0];
		String password = credentials.split("\\.")[1];
		 */
        Korisnik k;
        RegistrovaniKorisnik reg = userService.getUser(credentials.getUserName());
        Admin adm = adminService.getAdmin(credentials.getUserName());
        if(reg==null && adm==null)
            return null;
        
        else{
            k = (reg != null) ? reg: adm;
        }
/*

        Encryption e = encService.getEncrUser(k.getId());

        TextEncryptor decryptor = Encryptors.text("admin", e.getSalt());
        String decryptedText = decryptor.decrypt(e.getEncryptedPass());
        System.out.println("Decrypted text: \"" + decryptedText + "\"");
        System.out.println("Pass text: \"" + credentials.getPassword() + "\"");
*/

        if(credentials.getPassword().equals(k.getPassword())) {
            System.out.println("Success: decrypted text matches");
            if(k instanceof Admin && k.getPassword().equals("default") )
                k.setStatus(StatusNaloga.NERESEN);
            return k;
        } else {
            System.out.println("Failed: decrypted text does not match");
            return null;
        }


    }
    /*@RequestMapping(method = RequestMethod.POST, value = "/api/fblogin/username={username}&pass={password}&ime={ime}&prez={prez}")
    public Korisnik fblogin(@PathVariable String username, @PathVariable String password, @PathVariable String ime, @PathVariable String prez){
        Korisnik k;
        RegistrovaniKorisnik reg = userService.getUser(username);
        Admin adm = adminService.getAdmin(username);

        if(reg==null && adm==null){
            RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
            rk.setUserName(username);
            rk.setPassword(password);
            rk.setEmail(username);
            rk.setIme(ime);
            rk.setPassword(prez);
            rk.setStatus(StatusNaloga.AKTIVAN);
            System.out.println("opa" + rk.getUserName());
            userService.addUser(rk);
            return rk;
        }
        else k = (reg != null) ? reg: adm;
        return k;

    }*/
}
