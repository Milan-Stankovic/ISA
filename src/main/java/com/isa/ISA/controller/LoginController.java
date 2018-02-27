package com.isa.ISA.controller;

import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    public LoginController() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/login")
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://localhost:8096/login.html");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/login/username={username}&pass={password}")
    public Korisnik login(@PathVariable String username, @PathVariable String password){
        Korisnik k;
        RegistrovaniKorisnik reg = userService.getUser(username);
        Admin adm = adminService.getAdmin(username);
        if(reg==null && adm==null)
            return null;
        else{
            k = (reg != null) ? reg: adm;
        }
        if(k.getPassword().equals(password))
            return k;
        else
            return null;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/api/fblogin/username={username}&pass={password}&ime={ime}&prez={prez}")
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

    }
}
