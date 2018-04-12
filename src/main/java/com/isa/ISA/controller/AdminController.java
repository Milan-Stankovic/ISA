package com.isa.ISA.controller;

import java.util.List;

import com.isa.ISA.dbModel.PozoristeBioskop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.service.AdminService;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/api/admins")
    public List<Admin> getAllAdmins(){ return adminService.getAllAdmins(); }

    @RequestMapping(method = RequestMethod.POST,value = "/api/admins")
    public void addUser(@RequestBody Admin k){
        adminService.addAdmin(k);
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/admin/{username}")
    public Admin getAdmin(@PathVariable String username){ return adminService.getAdmin(username); }

    @RequestMapping("/admin/b/{id}")
    public List<PozoristeBioskop> getAdminBioskopi(@PathVariable Long id){ return adminService.getAdminB(id); }

    @RequestMapping("/admin/p/{id}")
    public List<PozoristeBioskop> getAdminPozorista(@PathVariable Long id){ return adminService.getAdminP(id); }

    @RequestMapping("/admin/{id}")
    public Admin getAdmin(@PathVariable Long id){
        return adminService.getAdmin(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value="/admin/{id}")
    public void updateAdmin(@RequestBody Admin a, @PathVariable Long id){
        adminService.updateAdmin(a);
    }

    @RequestMapping(method = RequestMethod.PUT,value="/admin/{id}/pass")
    public void updateAdminPassword(@RequestBody Admin a, @PathVariable Long id){
        adminService.updatePassword(a);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/{id}")
    public void deleteAdmin(@RequestBody Admin a, @PathVariable Long id){
        adminService.deleteAdmin(a);
    }


}