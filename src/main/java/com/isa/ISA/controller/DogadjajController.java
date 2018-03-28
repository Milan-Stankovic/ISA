package com.isa.ISA.controller;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.service.DogadjajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DogadjajController {

    @Autowired
    private DogadjajService dogService;

    @RequestMapping("/d")
    public List<Dogadjaj> getAllDogadjaj(){
        return dogService.getAllDogadjaj();
    }

    @RequestMapping("/d/{id}")
    public Dogadjaj getDogadjaj(@PathVariable Long id){
        return dogService.getDogadjaj(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/d")
    public void setDogadjaj(@RequestBody Dogadjaj d){
        dogService.addDogadjaj(d);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/d/{id}")
    public void updateDogadjaj(@PathVariable Long id, @RequestBody Dogadjaj d){
        dogService.updateDogadjaj(d);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/d/{id}")
    public void deleteDogadjaj(@PathVariable Long id){
        dogService.deleteDogadjaj(id);
    }
}
