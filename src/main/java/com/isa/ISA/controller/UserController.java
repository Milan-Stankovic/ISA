package com.isa.ISA.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.isa.ISA.DTO.InvitationDTO;
import com.isa.ISA.DTO.OneClickAfterDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.dbModel.enums.StatusPrijateljstva;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PrijateljService prijateljService;

	@Autowired
	private ReservationService resService;

	@Autowired
	private RezervacijaService rezService;

	@Autowired
	private ProjekcijaService projekcijaService;


    @RequestMapping(method = RequestMethod.GET,value = "/api/users")
    public List<RegistrovaniKorisnik> getAllUsers(){
        return userService.getAllUsers();
    }

	@RequestMapping(method = RequestMethod.PUT,value = "/api/user")
	public void addPoziv(@RequestBody OneClickAfterDTO ocad){
		 userService.updateUser(ocad);
	}
    
    @RequestMapping(method = RequestMethod.GET,value = "/api/user/{username}")
    public RegistrovaniKorisnik getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/api/users")
    public void addUser(@RequestBody RegistrovaniKorisnik k){
        userService.addUser(k);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/api/user/friends/{username}")
    public List<RegistrovaniKorisnik> deleteFriend(@RequestBody String email, @PathVariable String username){
    	List<RegistrovaniKorisnik> ret = new ArrayList<>();
    	RegistrovaniKorisnik k = userService.getUser(username);
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
    		
    		userService.addUser(k);
    		k.setPrijatelji(prijateljService.getUserFriends(k.getUserName()));
    		userService.addUser(k);
    		k = userService.getUser(username);
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
    
    @RequestMapping(method = RequestMethod.GET,value = "/api/user/friends/{username}")
    public List<RegistrovaniKorisnik> getUserFriends(@PathVariable String username){
    	List<RegistrovaniKorisnik> ret = new ArrayList<>();
    	RegistrovaniKorisnik k = userService.getUser(username);
		if(k==null)
			return ret;
    	k.setPrijatelji(prijateljService.getUserFriends(k.getUserName()));
    	userService.addUser(k);

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

	@RequestMapping(method = RequestMethod.POST,value = "/api/user/friends/{username}")
	public void addFriend(@PathVariable String username, @RequestBody String email) {
		RegistrovaniKorisnik posiljalac = userService.getUser(username);
		RegistrovaniKorisnik primalac = userService.findByEmail(email);
		Prijatelj p = new Prijatelj();
		p.setPosiljalac(posiljalac);
		p.setPrimalac(primalac);
		p.setStatus(StatusPrijateljstva.POSLATO);
		prijateljService.addFriendship(p);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/req/{username}")
	public List<RegistrovaniKorisnik> getUserReq(@PathVariable String username){

		RegistrovaniKorisnik k = userService.getUser(username);

		if(k==null)
			return new ArrayList<RegistrovaniKorisnik>();

		return prijateljService.getReqFriends(k.getUserName());


	}

    @RequestMapping(method = RequestMethod.GET,value = "/api/user/sent/{username}")
    public List<RegistrovaniKorisnik> getUserSent(@PathVariable String username){

        RegistrovaniKorisnik k = userService.getUser(username);

        if(k==null)
            return new ArrayList<RegistrovaniKorisnik>();

        return prijateljService.getSentFriends(k.getUserName());


    }

	@RequestMapping(method = RequestMethod.DELETE,value = "/api/user/decline/{username}")
	public List<RegistrovaniKorisnik> decline(@PathVariable String username, @RequestBody String email){
		RegistrovaniKorisnik primalac = userService.getUser(username);
		RegistrovaniKorisnik posiljalac = userService.findByEmail(email);
    	Prijatelj p = prijateljService.getPrimalacPosiljalac(primalac.getUserName(), posiljalac.getUserName());

		prijateljService.remove(p);

		return prijateljService.getReqFriends(primalac.getUserName());


	}

	@RequestMapping(method = RequestMethod.POST,value = "/api/user/accept/{username}")
	public List<RegistrovaniKorisnik> accept(@PathVariable String username, @RequestBody String email){
		RegistrovaniKorisnik primalac = userService.getUser(username);
		RegistrovaniKorisnik posiljalac = userService.findByEmail(email);
		Prijatelj p = prijateljService.getPrimalacPosiljalac(primalac.getUserName(), posiljalac.getUserName());
		p.setStatus(StatusPrijateljstva.PRIHVACENO);
		prijateljService.addFriendship(p);

		return prijateljService.getReqFriends(primalac.getUserName());


	}
	@RequestMapping(method = RequestMethod.GET,value = "/api/user/reservations/{username}")
	public List<Rezervacija> getRez(@PathVariable String username){
		RegistrovaniKorisnik kor = userService.getUser(username);
		if(kor==null)
			return new ArrayList<Rezervacija>();
		if(kor.getRezervacije()==null)
			return new ArrayList<Rezervacija>();
		return kor.getRezervacije();

    }

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/visited/{username}")
	public List<PozoristeBioskop> getVisitedP(@PathVariable String username){
		RegistrovaniKorisnik k = userService.getUser(username);
		if(k==null){
			return new ArrayList<PozoristeBioskop>();
		}
		List<Rezervacija> kRez = k.getRezervacije();
		List<Projekcija> kRezProjekcije  = new ArrayList<>();
		for(Rezervacija r : kRez)
			kRezProjekcije.add(r.getProjekcija());
		List<Sala> kRezSala  = new ArrayList<>();
		for(Projekcija p : kRezProjekcije)
			kRezSala.add(p.getSala());
		List<PozoristeBioskop> kRezUstanova  = new ArrayList<>();
		for(Sala s : kRezSala)
			kRezUstanova.add(s.getUstanova());
		return kRezUstanova;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/invitations/{username}")
	public List<Rezervacija> getInvitations(@PathVariable String username) {
		ArrayList<Rezervacija> ret = new ArrayList<>();
		RegistrovaniKorisnik reg = userService.getUser(username);
		if(reg==null)
			return new ArrayList<>();
		for(Rezervacija r : reg.getRezervacije()){
			for(Poziv p : r.getUrezervaciji()){
				if(p.getOsoba().getUserName().equals(username) && p.isPozvan() && p.getStatus().toString().equals("CEKA"))
					ret.add(r);
			}
		}
		return ret;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/invAccepted/{username}")
	public List<Rezervacija> getInvAccepted(@PathVariable String username) {
		ArrayList<Rezervacija> ret = new ArrayList<>();
		RegistrovaniKorisnik reg = userService.getUser(username);
		if(reg==null)
		    return new ArrayList<>();
        if(reg.getRezervacije()==null)
            reg.setRezervacije(new ArrayList<>());
		for(Rezervacija r : reg.getRezervacije()){
			for(Poziv p : r.getUrezervaciji()){
				if(p.getOsoba().getUserName().equals(username) && p.isPozvan() && p.getStatus().toString().equals("PRIHVACENO"))
					ret.add(r);
			}
		}
		return ret;
	}


/*

	@RequestMapping(method = RequestMethod.POST,value = "/api/user/invAccepted/{username}")
    public List<Rezervacija> getInvAccepted(@PathVariable String username, @RequestBody InvitationDTO invitationDTO) {
        RegistrovaniKorisnik reg = userService.getUser(username);
        if(invitationDTO.isAccepted()){
            System.out.println("Accepted!");
            int bodovi = 0;

            for(Rezervacija r : reg.getRezervacije())
                if(r.getId()==invitationDTO.getRezID())
                    for(Poziv p : r.getUrezervaciji())
                        if(p.getOsoba().getUserName().equals(username)){
                            p.setStatus(Status.PRIHVACENO);
                            rezService.addRez(r);
                            bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
                            break;
                        }
			bodovi = bodovi + reg.getBodovi();
            reg.setBodovi(bodovi);
            ArrayList<Rezervacija> samoPrihvacene = new ArrayList<>();
            for(Rezervacija r : reg.getRezervacije())
                if(r.getId()==invitationDTO.getRezID())
                    for(Poziv p : r.getUrezervaciji())
                        if(p.getOsoba().getUserName().equals(username))
                            if(!p.getStatus().toString().equals("ODBIJENO"))
                                samoPrihvacene.add(r);

            reg.setRezervacije(samoPrihvacene);
            userService.addUser(reg);
            return reg.getRezervacije();

        }
		int bodovi = 0;
		for(Rezervacija r : reg.getRezervacije())
			if(r.getId()==invitationDTO.getRezID())
				for(Poziv p : r.getUrezervaciji())
					if(p.getOsoba().getUserName().equals(username)){
						p.setStatus(Status.ODBIJENO);
						bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
						rezService.addRez(r);
						break;
					}
		bodovi = reg.getBodovi() - bodovi;
		if(bodovi<0) bodovi = 0;
		ArrayList<Rezervacija> samoPrihvacene = new ArrayList<>();
		for(Rezervacija r : reg.getRezervacije())
			if(r.getId()==invitationDTO.getRezID())
				for(Poziv p : r.getUrezervaciji())
					if(p.getOsoba().getUserName().equals(username))
						if(!p.getStatus().toString().equals("ODBIJENO"))
							samoPrihvacene.add(r);
		reg.setBodovi(bodovi);
		reg.setRezervacije(samoPrihvacene);
		userService.addUser(reg);
		return reg.getRezervacije();
	}

*/


}