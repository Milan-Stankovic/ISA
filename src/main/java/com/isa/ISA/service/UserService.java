package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepo;


    public List<RegistrovaniKorisnik> getAllUsers(){
        List<RegistrovaniKorisnik> allUsers = new ArrayList<>();
        usersRepo.findAll().forEach(allUsers::add);
        System.out.println(allUsers.size());
        return allUsers;

    }

    public RegistrovaniKorisnik getUser(String username){
        return usersRepo.findByUserName(username);
    }


    public void addUser(RegistrovaniKorisnik k){
        usersRepo.save(k);
    }



}
