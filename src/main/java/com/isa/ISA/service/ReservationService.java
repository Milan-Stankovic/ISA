package com.isa.ISA.service;

import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.RezervacijaRepository;
import com.isa.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;

@Service
public class ReservationService {

    @Autowired
    private RezervacijaRepository rezRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjekcijaService projekcijaService;


    public void addRez(Rezervacija r){
        rezRepo.save(r);
    }

    public Rezervacija getRez(Long id){
        return rezRepo.findOne(id);
    }
    @Transactional( readOnly = false,
            propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE)
    public void reserve(@RequestBody RezervacijaDTO k){

        RegistrovaniKorisnik rezervisao = userService.getUserID(k.getRezervisao());
        ArrayList<RegistrovaniKorisnik> pozvani = new ArrayList<>();
        for(Long kor : k.getPozvani()){
            pozvani.add(userService.getUserID(kor));
        }
        Projekcija projekcija = projekcijaService.getProjekcija(k.getProjekcija());
        Sala sala = projekcija.getSala();
        //temp sediste u koje stavljam ova trenutno zauzeta da bih lakse isla redom i dodeljivala svima u rezervaciji
        ArrayList<Sediste> temp = new ArrayList<>();
        for(Long sID : k.getSedista()){
            for(Sediste s : sala.getSedista()){
                if(s.getId()==sID){
                    s.setTipSedista(TipSedista.TAKEN);
                    projekcija.getZauzetaSedista().add(s);
                    temp.add(s);
                }
            }
        }
        Rezervacija rez = new Rezervacija();
        rez.setProjekcija(projekcija);
        rez.setPopust(k.getPopust());
        rez.setRezervisao(rezervisao);
        rezRepo.save(rez);
        //da bi rezervacija bila kompletna mora se ubaciti lista poziva, makar jedan za onog sto rezervise
        //kada imamo sve pozive, setujemo ih u rezervaciju i onda je ponovo cuvamo.
        //inicijalni poziv za onog koji rezervise:
        Poziv poz = new Poziv();
        poz.setRezervacija(rez);
        poz.setStatus(Status.PRIHVACENO);
        poz.setPozvan(false);
        poz.setOsoba(rezervisao);

        Karta ka = new Karta();
        //sa brojacem idemo od prvog do poslednjeg sedista, prvo u listi sedista se dodeljuje onom koji je rezervisao a ostala redom pozvanima
        int brojac = 0;
        ka.setSediste(temp.get(brojac));
        brojac++;
        ka.setVremeOdrzavanja(projekcija.getVreme());
        ka.setPozoristeBioskop(sala.getUstanova());
        int ukupno = (int) ( ((pozvani.size()+1)*projekcija.getCena()) - (k.getPopust()/100*((pozvani.size()+1)*projekcija.getCena()) ) );
        ka.setPunaCena(ukupno);

        poz.setKarta(ka);
        ArrayList<Poziv> pozivi = new ArrayList<>();
        pozivi.add(poz);
        for(RegistrovaniKorisnik reg : pozvani){
            Poziv p = new Poziv();
            p.setOsoba(reg);
            p.setPozvan(true);
            p.setStatus(Status.CEKA);
            p.setRezervacija(rez);

            Karta karta = new Karta();
            karta.setPozoristeBioskop(sala.getUstanova());
            karta.setVremeOdrzavanja(projekcija.getVreme());
            karta.setSediste(temp.get(brojac));
            brojac++;
            karta.setPunaCena(ukupno);

            p.setKarta(karta);
            pozivi.add(p);
        }
        rez.setUrezervaciji(pozivi);
        rezRepo.save(rez);
        rezervisao.getRezervacije().add(rez);
        userService.addUser(rezervisao);
        for(RegistrovaniKorisnik kor : pozvani){
            kor.getRezervacije().add(rez);
            userService.addUser(kor);
        }

    }
}