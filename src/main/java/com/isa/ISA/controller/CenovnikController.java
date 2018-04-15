package com.isa.ISA.controller;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.dbModel.CenovnikSedista;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.CenovnikSedistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CenovnikController {
    @Autowired
    private CenovnikSedistaRepository cenRepo;

    @RequestMapping(method = RequestMethod.POST,value = "/api/cenovnikSedista")
    public List<CenovnikSedista> getCen(@RequestBody Long id){

        return cenRepo.findBySalaId(id);
    }
}
