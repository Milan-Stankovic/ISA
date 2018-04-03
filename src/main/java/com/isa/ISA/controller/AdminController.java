package com.isa.ISA.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/api/admins")
    public List<Admin> getAllAdmins(){

        System.out.println("UPADOOOOH");

        return adminService.getAllAdmins();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/api/admins")
    public void addUser(@RequestBody Admin k){
        adminService.addAdmin(k);
    }

    @RequestMapping("/admin/{id}")
    public Admin getAdmin(@PathVariable Long id){
        return adminService.getAdmin(id);
    }











    public void updateAdmin(@RequestBody Admin a, @PathVariable Long id){
        adminService.updateAdmin(a);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/{id}")
    public void deleteAdmin(@RequestBody Admin a, @PathVariable Long id){
        adminService.deleteAdmin(a);
    }


}
