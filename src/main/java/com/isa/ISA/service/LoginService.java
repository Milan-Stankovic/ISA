package com.isa.ISA.service;

import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EncryptionService encService;

    public Korisnik login(RegKorDTO credentials) throws NoSuchAlgorithmException {
        Korisnik k;
        RegistrovaniKorisnik reg = userService.getUser(credentials.getUserName());
        Admin adm = adminService.getAdmin(credentials.getUserName());
        if(reg==null && adm==null)
            return null;

        else{
            k = (reg != null) ? reg: adm;
        }

        Encryption e = encService.getEncrUser(k.getId());
        if(e==null)
            return null;
        String test = Arrays.toString(encService.makeDigest(credentials.getPassword(),e.getSalt()));


        if(test.equals(k.getPassword())) {
            System.out.println("Success: decrypted text matches");
            String def = Arrays.toString(encService.makeDigest("default",e.getSalt()));
            if(k instanceof Admin && k.getPassword().equals(def) )
                k.setStatus(StatusNaloga.NERESEN);
            if(k.getStatus().toString().equals("NERESEN"))
                return null;
            else return k;
        } else {
            System.out.println("Failed: decrypted text does not match");
            return null;
        }

    }
}
