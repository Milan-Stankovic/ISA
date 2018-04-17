package com.isa.ISA.controller;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.DogadjajDTO;
import com.isa.ISA.DTO.FileDTO;
import com.isa.ISA.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.service.DogadjajService;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

@RestController
public class DogadjajController {

    @Autowired
    private DogadjajService dogService;

    @Autowired
    private UploadService upS;

    List<String> images = new ArrayList<String>();

    @RequestMapping("/do")
    public List<Dogadjaj> getAllDogadjaj() {
        return dogService.getAllDogadjaj();
    }

    @RequestMapping("/pb/dogadjaj/{id}")
    public List<Dogadjaj> getDogadjajbyPozBi(@PathVariable Long id){
        return dogService.findByPozBi(id);
    }

    @RequestMapping("/d/{id}")
    public Dogadjaj getDogadjaj(@PathVariable Long id){
        return dogService.getDogadjaj(id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/d")
    public void setDogadjaj(@RequestBody DogadjajDTO d){
        dogService.addDogadjaj(d);
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileDTO> upload(@RequestParam("file") MultipartFile file){

        FileDTO f = new FileDTO();
        String s = "";
        try {
            s = upS.add(file);
            System.out.println(file.getOriginalFilename());
            images.add(file.getOriginalFilename());

            System.out.println(s);

            f.setRezultat(s);
            return ResponseEntity.status(HttpStatus.OK).body(f);
        } catch (Exception e) {
            s = "FAILED";
            f.setRezultat(s);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(f);
        }

    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String id){
      Resource image=   upS.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/d/{id}")
    public void updateDogadjaj(@PathVariable Long id, @RequestBody DogadjajDTO d){
        dogService.updateDogadjaj(d, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/d/delete/{id}")
    public void deleteDogadjaj(@PathVariable Long id){
        dogService.deleteDogadjaj(id);
    }
}
