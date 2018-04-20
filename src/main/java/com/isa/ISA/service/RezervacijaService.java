package com.isa.ISA.service;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RezervacijaService {

    @Autowired
    private RezervacijaRepository rezRepo;

    @Autowired
    private PozivRepository pozRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProjekcijaService projekcijaService;



    public void delete(Long id){
        Rezervacija r = rezRepo.findOne(id);

        Poziv p = r.getUrezervaciji().get(0);
        r.getUrezervaciji().remove(0);
        pozRepo.delete( p.getId());
        rezRepo.delete(id);
    }

    public Rezervacija getRez(Long id){
        return rezRepo.findOne(id);
    }

    public List<Rezervacija> getRezByUser(String id){
        return rezRepo.findByRezervisaoUserName(id);
    }

    public List<Rezervacija> getAll(){
        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findAll().forEach(allP::add);
        return allP;

    }


    public List<Rezervacija> getQuckRezAdmin(Long pbId){

        List<Rezervacija> rez =rezRepo.findByRezervisao(null);
        System.out.println("NASAO REZERVACIJA : "+rez.size());


        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findByRezervisaoAndProjekcijaSalaUstanovaId(null, pbId).forEach(allP::add);
        //  for (Rezervacija r:allP){
        //    r.getProjekcija().ge

        //}

        return allP;

    }


    public List<Rezervacija> getQuckRez(Long pbId){
        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findByRezervisaoAndProjekcijaAktivnaAndProjekcijaSalaUstanovaId(null, true, pbId).forEach(allP::add); // opet mnogo kul ako radi
        System.out.println(allP.size());
      //  for (Rezervacija r:allP){
        //    r.getProjekcija().ge

        //}

        return allP;

    }


    public void addRez(Rezervacija r){
        rezRepo.save(r);
    }

    public ArrayList<Poziv> acceptInvitation(String username, Long id){
        RegistrovaniKorisnik reg = userService.getUser(username);
        System.out.println("ACCEPTED");
        int bodovi = 0;

        for(Rezervacija r : reg.getRezervacije())
            if(r.getId()==id)
                for(Poziv p : r.getUrezervaciji())
                    if(p.getOsoba().getUserName().equals(username)){
                        p.setStatus(Status.PRIHVACENO);
                        addRez(r);
                        bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
                        break;
                    }
        bodovi = bodovi + reg.getBodovi();
        reg.setBodovi(bodovi);
        ArrayList<Poziv> samoPrihvacene = new ArrayList<>();
        for(Rezervacija r : reg.getRezervacije())
            for(Poziv p : r.getUrezervaciji())
                if(p.getOsoba().getUserName().equals(username))
                    if(!p.getStatus().toString().equals("ODBIJENO"))
                        samoPrihvacene.add(p);

        userService.addUser(reg);
        return samoPrihvacene;
    }

    public ArrayList<Poziv> declineInvitation(String username, Long id){
        RegistrovaniKorisnik reg = userService.getUser(username);

        System.out.println("DECLINED");
        int bodovi = 0;
        Sediste zaVracanje = null;
        for(Rezervacija r : reg.getRezervacije())
            if(r.getId()==id)
                for(Poziv p : r.getUrezervaciji())
                    if(p.getOsoba().getUserName().equals(username)){
                        p.setStatus(Status.ODBIJENO);
                        pozRepo.save(p);
                        zaVracanje = p.getKarta().getSediste();
                        bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
                        addRez(r);
                        break;
                    }
        bodovi = reg.getBodovi() - bodovi;
        if(bodovi<0) bodovi = 0;
        ArrayList<Poziv> samoPrihvacene = new ArrayList<>();
        for(Rezervacija r : reg.getRezervacije())
            for(Poziv p : r.getUrezervaciji())
                if(p.getOsoba().getUserName().equals(username))
                    if(!p.getStatus().toString().equals("ODBIJENO"))
                        samoPrihvacene.add(p);
        reg.setBodovi(bodovi);
        userService.addUser(reg);

        if(zaVracanje!=null){
            Rezervacija rez = getRez(id);
            int idx = -1;
            for(int i = 0; i < rez.getProjekcija().getZauzetaSedista().size(); i++)
                if(rez.getProjekcija().getZauzetaSedista().get(i).getId()==zaVracanje.getId()) {
                    idx = i;
                    break;
                }

            if(idx!=-1)
                rez.getProjekcija().getZauzetaSedista().remove(idx);

            projekcijaService.addProjekcija(rez.getProjekcija());
            addRez(rez);
        }
        return samoPrihvacene;
    }
}
