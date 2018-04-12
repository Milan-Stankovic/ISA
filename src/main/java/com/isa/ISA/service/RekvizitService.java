package com.isa.ISA.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javassist.bytecode.ByteArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.repository.RekvizitRepository;

@Service
public class RekvizitService {
	@Autowired
    private RekvizitRepository rekvizitRepo;

	public List<ZvanicanRekvizit> getTematske() {
		return rekvizitRepo.findByAktivan(true);
	}

	public ZvanicanRekvizit addTematske(ZvanicanRekvizit zr) {
		return rekvizitRepo.save(zr);
	}

	public String saveMultipartFile(MultipartFile file) {
		final Path rootLocation = Paths.get("src/main/resources/static/assets/images/");
		String fileName = null;
		try {
			fileName = file.getOriginalFilename();
			if(!(fileName.endsWith(".JPG")|| fileName.endsWith(".jpg") || fileName.endsWith(".PNG") || fileName.endsWith(".png"))) {
				return fileName;
			}
			Path filePath = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() && (fileName.endsWith(".JPG")|| fileName.endsWith(".jpg"))) {
            	fileName = fileName.substring(0, fileName.length() - 4);
            		fileName = fileName + "_"  + System.currentTimeMillis() + ".jpg";
            }
            if(resource.exists() && (fileName.endsWith(".PNG")|| fileName.endsWith(".png"))) {
            	fileName = fileName.substring(0, fileName.length() - 4);
            	fileName = fileName + "_"  + System.currentTimeMillis() + ".png";
            }
            
            Files.copy(file.getInputStream(), rootLocation.resolve(fileName));
        } catch (Exception e) {
        	throw new RuntimeException();
        }
		return fileName;
	}

	public ZvanicanRekvizit editTematske(ZvanicanRekvizit zr) {
		return rekvizitRepo.save(zr);
	}

	public void deactivateTematske(long id) {
		ZvanicanRekvizit zr = (ZvanicanRekvizit) rekvizitRepo.findOne(id);
		zr.setAktivan(false);	
		rekvizitRepo.save(zr);
	}

}
