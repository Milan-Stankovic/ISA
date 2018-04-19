package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.DTO.AdminDTO;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.dbModel.BodovnaSkala;
import com.isa.ISA.dbModel.PolovanRekvizit;
import com.isa.ISA.dbModel.PozoristeBioskop;
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
    @RequestMapping(method = RequestMethod.POST,value = "/admins")
    public Admin addAdmin(@RequestBody AdminDTO admin){
        return adminService.addAdminBySis(admin);
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
    @RequestMapping(method = RequestMethod.PUT,value="/admininfo/{id}")
    public void updateInfoAdmin(@RequestBody RegKorDTO a, @PathVariable Long id){
        adminService.updateInfoAdmin(a, id);
    }
    @RequestMapping(method = RequestMethod.PUT,value="/admin/{id}/pass")
    public void updateAdminPassword(@RequestBody Admin a, @PathVariable Long id){
        adminService.updatePassword(a);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/{id}")
    public void deleteAdmin(@RequestBody Admin a, @PathVariable Long id){
        adminService.deleteAdmin(a);
    }
    
    
    @RequestMapping(method = RequestMethod.GET,value = "/fanAdmin/{id}/pb")
    public List<PozoristeBioskop> getFanPozBio(@PathVariable Long id){
        return adminService.getFanPozBio(id);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/fanAdmin/odobrenje")
    public PolovanRekvizit getPolovanZaOdobrenje(){
        return adminService.getPolovanZaOdobrenje();
    }
    @RequestMapping(method = RequestMethod.PUT,value = "/fanAdmin/odobreno/{id}")
    public PolovanRekvizit setPolovanOdobren(@PathVariable Long id){
        return adminService.setPolovanOdobren(id);
    }
    @RequestMapping(method = RequestMethod.PUT,value = "/fanAdmin/odbijeno/{id}")
    public PolovanRekvizit setPolovanOdbijen(@PathVariable Long id){
        return adminService.setPolovanOdbijen(id);
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/bodSkala")
    public BodovnaSkala getBodSkala(){
        return adminService.getBodSkala();
    }
    @RequestMapping(method = RequestMethod.POST,value = "/bodSkala")
    public BodovnaSkala setBodSkala(@RequestBody BodovnaSkala bs){
        return adminService.setBodSkala(bs);
    }

}