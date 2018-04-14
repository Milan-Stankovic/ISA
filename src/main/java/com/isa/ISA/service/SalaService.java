package com.isa.ISA.service;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService  {

    @Autowired
    private SalaRepository sRepo;

    public List<Sala> getAll(){
        List<Sala> allDog = new ArrayList<>();
        sRepo.findAll().forEach(allDog::add);
        return allDog;
    }

    public Sala getOne(Long id){
        return sRepo.findOne(id);
    }

    public void addSala(SalaDTO sd){

    }
}
