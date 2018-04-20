package com.isa.ISA.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.isa.ISA.dbModel.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.ISA.DTO.AdminDTO;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.dbModel.BodovnaSkala;
import com.isa.ISA.dbModel.PolovanRekvizit;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.StatusLicitacije;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.repository.AdminRepository;
import com.isa.ISA.repository.BodSkalaRepository;
import com.isa.ISA.repository.PolovanRekvRepository;
import com.isa.ISA.repository.PozoristeBioskopRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private PozoristeBioskopRepository pbRepo;
    @Autowired
    private BodSkalaRepository bsRepo;
    @Autowired
    private PolovanRekvRepository polovanRepo;
    @Autowired
    private EncryptionService encService;


    public List<Admin> getAllAdmins(){
        List<Admin> allAdmins = new ArrayList<>();
        adminRepo.findAll().forEach(allAdmins::add);
       // System.out.println(allAdmins.size());
        return allAdmins;
    }

    public List<PozoristeBioskop> getAdminP(Long id){

        List<PozoristeBioskop> sve = new ArrayList<>();
        sve= adminRepo.findOne(id).getMesta();
        List<PozoristeBioskop> pozorista = new ArrayList<>();

        for (PozoristeBioskop pb:sve) {
            if(pb.getTip() == TipUstanove.POZORISTE)
                pozorista.add(pb);
        }
        return pozorista;
    }

    public List<PozoristeBioskop> getAdminB(Long id){

        List<PozoristeBioskop> sve = new ArrayList<>();
        sve= adminRepo.findOne(id).getMesta();
        List<PozoristeBioskop> pozorista = new ArrayList<>();

        for (PozoristeBioskop pb:sve) {
            if(pb.getTip() == TipUstanove.BIOSKOP)
                pozorista.add(pb);
        }
        return pozorista;
    }

    public void addAdmin(Admin k){
        adminRepo.save(k);
    }

    public Admin getAdmin(String username){

        return adminRepo.findByUserName(username);
    }

    public Admin getAdmin(Long id){
        return adminRepo.findById(id);
    }

    public void updateAdmin(Admin a){
        adminRepo.save(a);
    }

    public void updatePassword(Admin a){
        a.setStatus(StatusNaloga.AKTIVAN);
        adminRepo.save(a);
    }

    public Admin getAdminByEmail(String email){
        return adminRepo.findByEmail(email);
    }

    public void deleteAdmin(Admin a){
        adminRepo.delete(a);
    }

    public List<Admin> findAllPBAdmin(){
        List<Admin> pbAdmins = new ArrayList<>();
        adminRepo.findByTip(TipAdmina.POZBI).addAll(pbAdmins);
        return pbAdmins;
    }

    public List<Admin> findAllSYSAdmin(){
        List<Admin> sysAdmin = new ArrayList<>();
        adminRepo.findByTip(TipAdmina.SYS).addAll(sysAdmin);
        return sysAdmin;
    }

    public List<Admin> findAllFanAdmin(){
        List<Admin> fanAdmin = new ArrayList<>();
        adminRepo.findByTip(TipAdmina.FAN).addAll(fanAdmin);
        return fanAdmin;
    }

	public Admin addAdminBySis(AdminDTO admin){
		Admin retVal  = new Admin();
		retVal.setUserName(admin.getUsername());
		retVal.setTip(admin.getTipAdmina());
		retVal.setPassword(admin.getPass());
		ArrayList<PozoristeBioskop> pb = new ArrayList<PozoristeBioskop>(); 
		if(admin.getPozBio()!=null){
			for(int i=0; i<admin.getPozBio().length; i++){
				pb.add(pbRepo.findOne(admin.getPozBio()[i]));
			}		
		}
		retVal.setMesta(pb);
		retVal.setEmail(admin.getEmail());
		retVal.setStatus(StatusNaloga.NERESEN);
		adminRepo.save(retVal);

        byte[] salt = encService.getNextSalt();
        byte[] newPass = new byte[0];
        try {
            newPass = encService.makeDigest(retVal.getPassword(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String pass = Arrays.toString(newPass);
        Encryption e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (adminRepo.findByUserName(retVal.getUserName()).getId() ));
        encService.addEncr(e);
        retVal.setPassword(pass);
        adminRepo.save(retVal);
        return retVal;

			
	}

	public BodovnaSkala getBodSkala() {
		ArrayList<BodovnaSkala> bss = (ArrayList<BodovnaSkala>)bsRepo.findAll();
		if(bss.size()==0)
			return null;
		BodovnaSkala retVal = bss.get(0);
		for(int i=0; i<bss.size(); i++){
			if(bss.get(i).getDatum().after(retVal.getDatum()))
				retVal = bss.get(i);
		}
		return retVal;
		
	}

	public BodovnaSkala setBodSkala(BodovnaSkala bs) {
		return bsRepo.save(bs);
	}

	public List<PozoristeBioskop> getFanPozBio(long id) {
		return adminRepo.findOne(id).getMesta();
	}

    @Transactional( readOnly = false,  propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public PolovanRekvizit getPolovanZaOdobrenje() {
		return polovanRepo.findFirstByStatusOrderByIdAsc(StatusLicitacije.POSTAVLJENO);
	}

	public PolovanRekvizit setPolovanOdobren(Long id) {
		PolovanRekvizit pr = polovanRepo.findOne(id);
		pr.setStatus(StatusLicitacije.UTOKU);
		return polovanRepo.save(pr);
	}

	public PolovanRekvizit setPolovanOdbijen(Long id) {
		PolovanRekvizit pr = polovanRepo.findOne(id);
		pr.setStatus(StatusLicitacije.ODBIJENO);
		return polovanRepo.save(pr);
	}

	public void updateInfoAdmin(RegKorDTO a, long id){
		Admin adm = adminRepo.findById(id);
		adm.setBrojTelefona(a.getBrojTelefona());
		adm.setGrad(a.getGrad());
		adm.setPrezime(a.getPrezime());
		adm.setIme(a.getIme());
        if(!(a.getPassword().isEmpty() || a.getPassword()==null))
            adm.setPassword(a.getPassword());
        Encryption e = encService.getEncrUser(adm.getId());
        System.out.println("enc pass: " + Arrays.toString(e.getEncryptedPass()));
        System.out.println("got pass: " + adm.getPassword());

        if(Arrays.toString(e.getEncryptedPass()).equals(adm.getPassword())){
            System.out.println("NIJe menjao pass");
        }else{
            System.out.println("JEST menjao pass");
            byte[] salt = encService.getNextSalt();
            byte[] newPass = new byte[0];
            try {
                newPass = encService.makeDigest(adm.getPassword(), salt);
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
            String pass = Arrays.toString(newPass);
            System.out.println(pass);
            e.setSalt(salt);
            e.setEncryptedPass(newPass);
            encService.addEncr(e);
            adm.setPassword(pass);

        }



		adminRepo.save(adm);
		
	}



}
