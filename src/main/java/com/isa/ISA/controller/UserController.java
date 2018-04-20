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
    	return userService.deleteFriend(email,username);
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/api/user/friends/{username}")
    public List<RegistrovaniKorisnik> getUserFriends(@PathVariable String username){
    	return userService.getUserFriends(username);
    }

	@RequestMapping(method = RequestMethod.POST,value = "/api/user/friends/{username}")
	public void addFriend(@PathVariable String username, @RequestBody String email) {
		userService.addFriend(username, email);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/req/{username}")
	public List<RegistrovaniKorisnik> getUserReq(@PathVariable String username){
    	return userService.getFriendRequests(username);
	}

    @RequestMapping(method = RequestMethod.GET,value = "/api/user/sent/{username}")
    public List<RegistrovaniKorisnik> getUserSent(@PathVariable String username){
		return userService.getSentRequests(username);
    }

	@RequestMapping(method = RequestMethod.DELETE,value = "/api/user/decline/{username}")
	public List<RegistrovaniKorisnik> decline(@PathVariable String username, @RequestBody String email){
		return userService.declineFriend(username, email);
	}

	@RequestMapping(method = RequestMethod.POST,value = "/api/user/accept/{username}")
	public List<RegistrovaniKorisnik> accept(@PathVariable String username, @RequestBody String email){
		return userService.acceptFriend(username, email);

	}
	@RequestMapping(method = RequestMethod.GET,value = "/api/user/reservations/{username}")
	public List<Rezervacija> getRez(@PathVariable String username){
		return userService.getAllReservations(username);

    }

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/resPozivi/{username}")
	public List<Poziv> getRezPoz(@PathVariable String username){
		return userService.getPoziviRez(username);

	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/visited/{username}")
	public List<PozoristeBioskop> getVisitedP(@PathVariable String username){
		return userService.getVisitedPlaces(username);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/invitations/{username}")
	public List<Poziv> getInvitations(@PathVariable String username) {
    	return userService.getInvitations(username);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/api/user/invAccepted/{username}")
	public List<Poziv> getInvAccepted(@PathVariable String username) {
		return userService.getAcceptedInv(username);
	}


}