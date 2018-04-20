package com.isa.ISA.controller;

import com.isa.ISA.DTO.CenovnikDTO;
import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.dbModel.CenovnikSedista;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.CenovnikSedistaRepository;
import com.isa.ISA.service.CenovnikService;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CenovnikController {
    @Autowired
    private CenovnikService cenRepo;

    @RequestMapping(method = RequestMethod.POST,value = "/api/cenovnikSedista") // Post koji je get ? o.O
    public List<CenovnikSedista> getCen(@RequestBody Long id){

        return cenRepo.getCenovnici(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/api/cenovnikSedista/{id}")
    public void setUpdate(@RequestBody CenovnikDTO cene, @PathVariable Long id){
        cenRepo.setUpdatePrice(cene, id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/api/cenovnikSedista/{id}")
    public CenovnikDTO getCenovniciDTO(@PathVariable Long id){
        return cenRepo.getDTO(id);
    }


}
