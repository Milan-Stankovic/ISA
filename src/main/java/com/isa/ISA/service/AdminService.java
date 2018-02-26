package com.isa.ISA.service;

import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.AdminRepository;
import com.isa.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;


    public List<Admin> getAllAdmins(){
        List<Admin> allAdmins = new ArrayList<>();
        adminRepo.findAll().forEach(allAdmins::add);
        System.out.println(allAdmins.size());
        return allAdmins;
    }

    public void addAdmin(Admin k){
        adminRepo.save(k);
    }

    public Admin getAdmin(String username){
        return adminRepo.findByUserName(username);
    }

}
