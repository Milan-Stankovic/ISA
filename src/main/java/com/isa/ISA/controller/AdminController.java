package com.isa.ISA.controller;

import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admins")
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/admins")
    public void addUser(@RequestBody Admin k){
        adminService.addAdmin(k);
    }
}
