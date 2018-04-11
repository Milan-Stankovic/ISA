package com.isa.ISA.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javassist.bytecode.ByteArray;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void addTematske(ZvanicanRekvizit zr) {
		zr.setRezervacije(null);
		rekvizitRepo.save(zr);
	}

	public byte[] saveMultipartFile(MultipartFile files) {
		if(!files.isEmpty()){	        
            byte[] bytes;
			try {
				bytes = files.getBytes();
	            return bytes;
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		return null;
	}

}
