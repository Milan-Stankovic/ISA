package com.isa.ISA.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isa.ISA.DTO.PolovanRekvDTO;
import com.isa.ISA.DTO.PonudaDTO;
import com.isa.ISA.DTO.RekvizitDTO;
import com.isa.ISA.dbModel.Obavestenje;
import com.isa.ISA.dbModel.PolovanRekvizit;
import com.isa.ISA.dbModel.Ponuda;
import com.isa.ISA.dbModel.RezervacijaRekvizita;
import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.dbModel.enums.StatusLicitacije;
import com.isa.ISA.repository.AdminRepository;
import com.isa.ISA.repository.ObavestenjeRepository;
import com.isa.ISA.repository.PolovanRekvRepository;
import com.isa.ISA.repository.PonudaRepository;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import com.isa.ISA.repository.RekvizitRepository;
import com.isa.ISA.repository.UserRepository;

@Service
public class RekvizitService {
	@Autowired
    private RekvizitRepository rekvizitRepo;
	@Autowired
    private PolovanRekvRepository polovanRepo;

	@Autowired
    private AdminRepository adminRepo;
	
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
    private PozoristeBioskopRepository pbRepo;

	@Autowired
    private PonudaRepository ponudaRepo;
	
	@Autowired
    private ObavestenjeRepository obavRepo;
	
	
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
		return "assets/images/"+fileName;
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

	public ZvanicanRekvizit rezervisiTematske(long id, long userID) {
		ZvanicanRekvizit retVal = rekvizitRepo.findOne(id);
		RezervacijaRekvizita rez = new RezervacijaRekvizita();
		rez.setIzvrsio(userRepo.findOne(userID));
		rez.setDatum(new Time(System.currentTimeMillis()));
		rez.setReseno(false);
		
		retVal.getRezervacije().add(rez);
		return rekvizitRepo.save(retVal);
	}

	public PolovanRekvizit addPolovan(PolovanRekvDTO rekDTO) {
		PolovanRekvizit retVal = new PolovanRekvizit();
		retVal.setKraj(rekDTO.getDatum());
		retVal.setLicitacija(new ArrayList<Ponuda>());
		retVal.setNaziv(rekDTO.getNaziv());
		retVal.setOpis(rekDTO.getOpis());
		retVal.setPostavio(userRepo.findByUserName(rekDTO.getUsername()));
		retVal.setStatus(StatusLicitacije.POSTAVLJENO);
		retVal.setSlika(rekDTO.getSlika());
		return polovanRepo.save(retVal);
	}

	public List<PolovanRekvizit> getPolovan(long userID) {
		ArrayList<PolovanRekvizit> all = (ArrayList<PolovanRekvizit>)polovanRepo.findByStatus(StatusLicitacije.UTOKU);
		ArrayList<PolovanRekvizit> retVal = new ArrayList<PolovanRekvizit>();
		for(PolovanRekvizit pr : all){
			if(pr.getPostavio().getId()!=userID)
				retVal.add(pr);
		}
		return retVal;
	}

	public PolovanRekvizit ponudiPolovne(PonudaDTO p) {
		PolovanRekvizit retVal = polovanRepo.findOne(p.getIdRekvizita());
		Ponuda p0 = new Ponuda();
		p0.setPonudio(p.getUsername());
		p0.setPrihvaceno(false);
		p0.setRekvizit(retVal);
		p0.setSuma(p.getCena());
		retVal.getLicitacija().add(p0);
		ponudaRepo.save(p0);
		return polovanRepo.save(retVal);
	}

	public void updatePonude(PonudaDTO p) {
		Ponuda p0 = ponudaRepo.findByPonudioAndRekvizitId(p.getUsername(), p.getIdRekvizita());
		p0.setSuma(p.getCena());
		ponudaRepo.save(p0);
	}

	public List<PolovanRekvizit> getMojiOglasi(long userID) {
		return polovanRepo.findByPostavioId(userID);
	}

	public List<PolovanRekvizit> getMojePonude(String username) {
		return polovanRepo.findByLicitacijaPonudio(username);
	}

	public PolovanRekvizit updatePolovan(PolovanRekvDTO rekDTO, long id) {
		PolovanRekvizit retVal = polovanRepo.findOne(id);
		retVal.setKraj(rekDTO.getDatum());
		retVal.setNaziv(rekDTO.getNaziv());
		retVal.setOpis(rekDTO.getOpis());
		if(!rekDTO.getSlika().equals(""))
			retVal.setSlika(rekDTO.getSlika());
		return polovanRepo.save(retVal);
	}

	public PolovanRekvizit prihvatiPonudu(long itemID, long id) {
		PolovanRekvizit retVal = polovanRepo.findOne(itemID);
		retVal.setStatus(StatusLicitacije.ZAVRSENA);
		Ponuda p = ponudaRepo.findByRekvizitIdAndId(itemID, id);
		p.setPrihvaceno(true);
		ponudaRepo.save(p);
		for(int i=0; i<retVal.getLicitacija().size(); i++){
			Ponuda p0 = retVal.getLicitacija().get(i);
			Obavestenje o = new Obavestenje();
			String tekst = "Your offer ["+p0.getSuma()+" RSD] for item on sale - "+retVal.getNaziv()+" has been DENIED. "
					+ "You can check out new user sales and win next time! Good luck, your Cinema&Theatre Team";
			if(p0.getId()==id){
				tekst="Congratulations! Your offer ["+p0.getSuma()+" RSD] for item on sale - "+retVal.getNaziv()+
						" has WON. Here are sellers contact info:"+
						retVal.getPostavio().getIme()+" "+retVal.getPostavio().getPrezime()+", phone: "+
						retVal.getPostavio().getBrojTelefona()+", email:"+retVal.getPostavio().getEmail();			
			}
			o.setDeleted(false);
			o.setPonuda(p0);
			o.setPrima(p0.getPonudio());
			o.setTekst(tekst);
			obavRepo.save(o);
		}
		return polovanRepo.save(retVal);
	}

}
