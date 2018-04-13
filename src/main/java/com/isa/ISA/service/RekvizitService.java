package com.isa.ISA.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isa.ISA.DTO.RekvizitDTO;
import com.isa.ISA.dbModel.RezervacijaRekvizita;
import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.repository.AdminRepository;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import com.isa.ISA.repository.RekvizitRepository;

@Service
public class RekvizitService {
	@Autowired
    private RekvizitRepository rekvizitRepo;

	@Autowired
    private AdminRepository adminRepo;
	
	@Autowired
    private PozoristeBioskopRepository pbRepo;
	
	
	public List<ZvanicanRekvizit> getTematske() {
		return rekvizitRepo.findByAktivan(true);
	}

	public ZvanicanRekvizit addTematske(RekvizitDTO zr) {
		ZvanicanRekvizit retVal = new ZvanicanRekvizit();
		retVal.setAktivan(true);
		retVal.setCena(zr.getCena());
		retVal.setId(0);
		retVal.setNaziv(zr.getNaziv());
		retVal.setOpis(zr.getOpis());
		retVal.setPostavio(adminRepo.findByUserName(zr.getAdmin()));
		retVal.setPreuzeti(pbRepo.findOne(zr.getPozBioID()));
		retVal.setRezervacije(new ArrayList<RezervacijaRekvizita>());
		retVal.setSlika(zr.getSlika());
		return rekvizitRepo.save(retVal);
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

	public ZvanicanRekvizit editTematske(RekvizitDTO zr, long id) {
		ZvanicanRekvizit retVal = rekvizitRepo.findOne(id);
		retVal.setCena(zr.getCena());
		retVal.setNaziv(zr.getNaziv());
		retVal.setOpis(zr.getOpis());
		if(zr.getSlika()!="")
			retVal.setSlika(zr.getSlika());
		retVal.setPreuzeti(pbRepo.findOne(zr.getPozBioID()));
		retVal.setPostavio(adminRepo.findByUserName(zr.getAdmin()));
		return rekvizitRepo.save(retVal);
	}

	public void deactivateTematske(long id) {
		ZvanicanRekvizit zr = (ZvanicanRekvizit) rekvizitRepo.findOne(id);
		zr.setAktivan(false);	
		rekvizitRepo.save(zr);
	}

}
