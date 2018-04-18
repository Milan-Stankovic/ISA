package com.isa.ISA.controller;

import java.util.Date;
import java.util.Map;

import com.isa.ISA.DTO.PrimljenIzvestajDTO;
import com.isa.ISA.DTO.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.service.IzvestajSevice;

@RestController
public class IzvestajController {

    @Autowired
    private IzvestajSevice izvestajSevice;

    @RequestMapping("/izvestaj/pb/{id}/ambijent")
    private float getAmbijent(@PathVariable Long id){
        return izvestajSevice.getOcenaAmbijenta(id);
    }

    @RequestMapping("/izvestaj/dogadjaj/{id}/ocena")
    private float getOcenaPredstava(@PathVariable Long id){
        return izvestajSevice.getOcenaDogadjaj(id);
    }

    @RequestMapping("/izvestaj/dogadjaj/{id}/prihodIzmedju")
    private int getPrihodIzmedju(@PathVariable Long id, @RequestBody Date od, @RequestBody  Date d2){
        return izvestajSevice.getPrihodIzmedju(id, od, d2);
    }

    @RequestMapping("/izvestaj/dogadjaj/{id}/prihodOd")
    private int getPrihodOd(@PathVariable Long id,@RequestBody  Date od){
        return izvestajSevice.getPrihodPosle(id, od);
    }

    @RequestMapping("/izvestaj/dogadjaj/{id}/prihodDo")
    private int getPrihodDo(@PathVariable Long id,@RequestBody  Date d2){
        return izvestajSevice.getPrihodPre(id, d2);
    }

    @RequestMapping("/izvestaj/pb/{id}/posecenostOd")
    private Map<Date, Integer> getPosecenostOd(@PathVariable Long id,@RequestBody  Date od){
        return izvestajSevice.getPoseteOd(id,od);
    }

    @RequestMapping("/izvestaj/pb/{id}/posecenostDo")
    private Map<Date, Integer> getPosecenostDo(@PathVariable Long id,@RequestBody  Date d2){
        return izvestajSevice.getPoseteDo(id,d2);
    }

    @RequestMapping("/izvestaj/pb/{id}/posecenostIzmedju")
    private Map<Date, Integer> getPosecenostIzmedju(@PathVariable Long id,@RequestBody  Date od,@RequestBody  Date d2){
        return izvestajSevice.getPoseteOdDo(id,od, d2);
    }

    @RequestMapping(method =RequestMethod.POST ,value ="/fullIzvestaj")
    private PrimljenIzvestajDTO getfullIzvestaj(@RequestBody ReportDto r){
        return izvestajSevice.makeReport(r);
    }





}
