package com.isa.ISA.controller;

import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET,value = "/api/users")
    public List<RegistrovaniKorisnik> getAllUsers(){
        return userService.getAllUsers();
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/api/user/{username}")
    public RegistrovaniKorisnik getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/api/users")
    public void addUser(@RequestBody RegistrovaniKorisnik k){
        userService.addUser(k);
    }


}
