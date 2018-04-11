package com.isa.ISA.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.UserService;

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

    @RequestMapping(method = RequestMethod.GET,value = "/api/user/friends/{username}")
    public List<RegistrovaniKorisnik> getUserFriends(@PathVariable String username){
    	List<RegistrovaniKorisnik> ret = new ArrayList<>();
    	RegistrovaniKorisnik k = userService.getUser(username);
    	if(k==null)
    		return ret;
    	if(k.getPrijatelji()!=null){
    		System.out.println("B prijatelja: " + k.getPrijatelji().size());
    		if(k.getPrijatelji().size()>0){
    			for(Prijatelj p : k.getPrijatelji()){
    				if(p.getPosiljalac().getUserName().equals(username)){
    					ret.add(p.getPrimalac());
    				}
    				if(p.getPrimalac().getUserName().equals(username)){
    					ret.add(p.getPosiljalac());
    				}
    			}
    		}
    	}
    	return ret;
        
    }
    

}