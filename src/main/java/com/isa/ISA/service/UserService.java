package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.OneClickAfterDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.enums.StatusPrijateljstva;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepo;

    @Autowired
    private PrijateljService prijateljService;

    @Autowired
    private ReservationService resService;

    @Autowired
    private RezervacijaService rezService;

    @Autowired
    private ProjekcijaService projekcijaService;


    public List<RegistrovaniKorisnik> getAllUsers(){
        List<RegistrovaniKorisnik> allUsers = new ArrayList<>();
        usersRepo.findAll().forEach(allUsers::add);

        return allUsers;

    }

    public RegistrovaniKorisnik getUser(String username){
        return usersRepo.findByUserNameIgnoreCase(username);
    }

    public RegistrovaniKorisnik getUserID(Long id){
        return usersRepo.findById(id);
    }


    public void addUser(RegistrovaniKorisnik k){
        usersRepo.save(k);
    }

	public RegistrovaniKorisnik findByEmail(String email) {
		// TODO Auto-generated method stub
		return usersRepo.findByEmail(email);
	}

	public void updateUser(OneClickAfterDTO ocad){
        Poziv p = new Poziv();
        p.setId(ocad.getPozivId());
     //   System.out.println(p.getId());
        Rezervacija r = new Rezervacija();
        r.setId(ocad.getRezervacijaID());
        RegistrovaniKorisnik reg = (RegistrovaniKorisnik) usersRepo.findOne(ocad.getUserId());
      //  System.out.println(reg.getId());
       // System.out.println(reg.getPozivi().size());
        reg.getPozivi().add(p);
      //  System.out.println(reg.getPozivi().size());
        reg.getRezervacije().add(r);
        usersRepo.save(reg);
    }
	
	public List<RegistrovaniKorisnik> searchImePrezime(String ime, String prezime){
        List<RegistrovaniKorisnik> imena = searchIme(ime);
        List<RegistrovaniKorisnik> prez = searchPrezime(prezime);
        List<RegistrovaniKorisnik> ret = new ArrayList<>();
        for(RegistrovaniKorisnik i : imena)
            for(RegistrovaniKorisnik p :prez)
                if(i.getId()==p.getId())
                    ret.add(p);
		return ret;
	}
	public List<RegistrovaniKorisnik> searchIme(String ime){
		return usersRepo.findByImeIgnoreCase(ime);
	}
	public List<RegistrovaniKorisnik> searchPrezime(String prezime){
		return usersRepo.findByPrezimeIgnoreCase(prezime);
	}

    public List<RegistrovaniKorisnik> deleteFriend(String email, String username){
        List<RegistrovaniKorisnik> ret = new ArrayList<>();
        RegistrovaniKorisnik k = getUser(username);
        if(k==null)
            return ret;

        if(k.getPrijatelji()!=null){

            System.out.println("bez brisanja prijatelja: " + k.getPrijatelji().size());
            if(k.getPrijatelji().size()>0){
                for(Prijatelj p : k.getPrijatelji()){
                    if(p.getPosiljalac().getEmail().equals(email) || p.getPrimalac().getEmail().equals(email)){
                        System.out.println("nasao mejl: " + email);
                        p.setStatus(StatusPrijateljstva.ODBIJENO);
                        prijateljService.addFriendship(p);
                    }

                }
            }

            addUser(k);
            k.setPrijatelji(prijateljService.getUserFriends(k.getUserName()));
            addUser(k);
            k = getUser(username);
            if(k.getPrijatelji().size()>0){
                for(Prijatelj p : k.getPrijatelji()){

                    if(p.getPosiljalac().getUserName().equals(username)){
                        ret.add(p.getPrimalac());
                    }
                    if(p.getPrimalac().getUserName().equals(username)){
                        ret.add(p.getPosiljalac());
                    }


                }
            }
        }
        return ret;
    }

    public List<RegistrovaniKorisnik> getUserFriends(String username) {
        List<RegistrovaniKorisnik> ret = new ArrayList<>();
        RegistrovaniKorisnik k = getUser(username);
        if(k==null)
            return ret;
        k.setPrijatelji(prijateljService.getUserFriends(k.getUserName()));
        addUser(k);

        if(k.getPrijatelji()!=null){
            System.out.println("B prijatelja: " + k.getPrijatelji().size());
            if(k.getPrijatelji().size()>0){
                for(Prijatelj p : k.getPrijatelji()){
                    if(p.getPosiljalac().getUserName().equals(username)){
                        ret.add(p.getPrimalac());
                    }
                    if(p.getPrimalac().getUserName().equals(username)){
                        ret.add(p.getPosiljalac());
                    }
                }
            }
        }
        return ret;

    }

    public void addFriend(String username, String email) {
        RegistrovaniKorisnik posiljalac = getUser(username);
        RegistrovaniKorisnik primalac = findByEmail(email);
        Prijatelj p = new Prijatelj();
        p.setPosiljalac(posiljalac);
        p.setPrimalac(primalac);
        p.setStatus(StatusPrijateljstva.POSLATO);
        prijateljService.addFriendship(p);
    }

    public List<RegistrovaniKorisnik> getFriendRequests(String username) {

        RegistrovaniKorisnik k = getUser(username);

        if(k==null)
            return new ArrayList<RegistrovaniKorisnik>();

        return prijateljService.getReqFriends(k.getUserName());

    }


    public List<RegistrovaniKorisnik> getSentRequests(String username) {

        RegistrovaniKorisnik k = getUser(username);

        if(k==null)
            return new ArrayList<RegistrovaniKorisnik>();

        return prijateljService.getSentFriends(k.getUserName());


    }

    public List<RegistrovaniKorisnik> declineFriend(String username, String email) {
        RegistrovaniKorisnik primalac = getUser(username);
        RegistrovaniKorisnik posiljalac = findByEmail(email);
        Prijatelj p = prijateljService.getPrimalacPosiljalac(primalac.getUserName(), posiljalac.getUserName());

        prijateljService.remove(p);

        return prijateljService.getReqFriends(primalac.getUserName());


    }


    public List<RegistrovaniKorisnik> acceptFriend(String username, String email) {
        RegistrovaniKorisnik primalac = getUser(username);
        RegistrovaniKorisnik posiljalac = findByEmail(email);
        Prijatelj p = prijateljService.getPrimalacPosiljalac(primalac.getUserName(), posiljalac.getUserName());
        p.setStatus(StatusPrijateljstva.PRIHVACENO);
        prijateljService.addFriendship(p);

        return prijateljService.getReqFriends(primalac.getUserName());

    }


    public List<Rezervacija> getAllReservations(String username) {
        RegistrovaniKorisnik kor = getUser(username);
        if(kor==null)
            return new ArrayList<Rezervacija>();
        if(kor.getRezervacije()==null)
            return new ArrayList<Rezervacija>();
        return kor.getRezervacije();
    }

    public List<Poziv> getPoziviRez(String username) {
        RegistrovaniKorisnik kor = getUser(username);
        ArrayList<Poziv> ret = new ArrayList<>();
        if(kor==null)
            return new ArrayList<Poziv>();
        if(kor.getRezervacije()==null)
            return new ArrayList<Poziv>();
        for(Rezervacija r : kor.getRezervacije())
            for(Poziv p: r.getUrezervaciji())
                if(p.getOsoba().getUserName().equals(username) && !p.getStatus().toString().equals("ODBIJENO"))
                    ret.add(p);
        return ret;
    }


    public List<PozoristeBioskop> getVisitedPlaces(String username) {
        RegistrovaniKorisnik k = getUser(username);
        List<PozoristeBioskop> kRezUstanova  = new ArrayList<>();
        if(k==null){
            return new ArrayList<PozoristeBioskop>();
        }
        for(Rezervacija r : k.getRezervacije()){
            for(Poziv p : r.getUrezervaciji())
                if(p.getOsoba().getUserName().equals(username))
                    if(!p.getStatus().toString().equals("ODBIJENO"))
                        kRezUstanova.add(p.getRezervacija().getProjekcija().getSala().getUstanova());
        }
        return kRezUstanova;
    }


    public List<Poziv> getInvitations(String username) {
        ArrayList<Poziv> ret = new ArrayList<>();
        RegistrovaniKorisnik reg = getUser(username);
        if(reg==null)
            return new ArrayList<>();
        for(Rezervacija r : reg.getRezervacije()){
            for(Poziv p : r.getUrezervaciji()){
                if(p.getOsoba().getUserName().equals(username) && p.isPozvan() && p.getStatus().toString().equals("CEKA"))
                    ret.add(p);
            }
        }
        return ret;
    }

    public List<Poziv> getAcceptedInv(String username) {
        ArrayList<Poziv> ret = new ArrayList<>();
        RegistrovaniKorisnik reg = getUser(username);
        if(reg==null)
            return new ArrayList<>();
        if(reg.getRezervacije()==null)
            reg.setRezervacije(new ArrayList<>());
        for(Rezervacija r : reg.getRezervacije()){
            for(Poziv p : r.getUrezervaciji()){
                if(p.getOsoba().getUserName().equals(username) && p.getStatus().toString().equals("PRIHVACENO"))
                    ret.add(p);
            }
        }
        return ret;
    }
}
