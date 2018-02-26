package com.isa.ISA;

import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartData {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @PostConstruct
    public void initIt() throws Exception {

        RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
        rk.setUserName("kor");
        rk.setPassword("kor");
        rk.setEmail("kor");
        rk.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + rk.getUserName());
        userService.addUser(rk);

        Admin a = new Admin();
        a.setUserName("admin");
        a.setPassword("admin");
        a.setTip(TipAdmina.SYS);
        a.setEmail("admin");
        a.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + a.getUserName());
        adminService.addAdmin(a);
    }
}
