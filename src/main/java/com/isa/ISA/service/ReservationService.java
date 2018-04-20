package com.isa.ISA.service;

import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozivRepository;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class ReservationService {

    @Autowired
    private RezervacijaService rezService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private PozivRepository pozRepo;

    @Autowired
    private KartaRepository kartaRepo;

    @Autowired
    private EmailService em;

    public void addRez(Rezervacija r){
        rezService.addRez(r);
    }

    public Rezervacija getRez(Long id){
        return rezService.getRez(id);
    }
    @Transactional( readOnly = false,  propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void reserve(@RequestBody RezervacijaDTO k) throws InterruptedException {
        RegistrovaniKorisnik rezervisao = userService.getUserID(k.getRezervisao());
        Projekcija projekcija = projekcijaService.getProjekcijaID((long) k.getProjekcija());
        Sala sala = projekcija.getSala();
        //temp sediste u koje stavljam ova trenutno zauzeta da bih lakse isla redom i dodeljivala svima u rezervaciji
        ArrayList<Sediste> temp = new ArrayList<>();
        for (Long sID : k.getSedista()) {
            for (Sediste s : sala.getSedista()) {
                if (s.getId() == sID) {
                    if(projekcija.getZauzetaSedista()==null)
                        projekcija.setZauzetaSedista(new ArrayList<Sediste>());
                    projekcija.getZauzetaSedista().add(s);
                    temp.add(s);
                }
            }
        }
        projekcijaService.addProjekcija(projekcija);
        Rezervacija rez = new Rezervacija();
        rez.setProjekcija(projekcija);
        rez.setPopust(k.getPopust());
        rez.setRezervisao(rezervisao);

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
        int ukupno = (int) ((k.getSedista().size() * projekcija.getCena()) - (k.getPopust() / 100 * (k.getSedista().size() * projekcija.getCena())));
        ka.setPunaCena(ukupno);
        kartaRepo.save(ka);
        poz.setKarta(ka);
        pozRepo.save(poz);

        ArrayList<Poziv> pozivi = new ArrayList<>();
        pozivi.add(poz);
        rezService.addRez(rez);

        rez.setUrezervaciji(pozivi);
        rezService.addRez(rez);
        rezervisao.getRezervacije().add(rez);
        int bodova = rezervisao.getBodovi() + rez.getProjekcija().getDogadjaj().getDonosiBodova();
        rezervisao.setBodovi(bodova);
        userService.addUser(rezervisao);


        ArrayList<RegistrovaniKorisnik> pozvani = new ArrayList<>();
        if (k.getPozvani() != null) {
            for (Long kor : k.getPozvani()) {
                pozvani.add(userService.getUserID(kor));
            }
        }

        for (RegistrovaniKorisnik reg : pozvani) {

            reg.getRezervacije().add(rez);
            bodova = reg.getBodovi() + rez.getProjekcija().getDogadjaj().getDonosiBodova();
            reg.setBodovi(bodova);
            userService.addUser(reg);
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
            kartaRepo.save(karta);
            p.setKarta(karta);
            pozRepo.save(p);
            pozivi.add(p);

        }
        rez.setUrezervaciji(pozivi);
        rezService.addRez(rez);



        for(Poziv po : pozivi){
            if(po.isPozvan()){
                String s = "Please click here to accept or decline your invitation: http://localhost:8096/#!/allReservations";
                em.inviteEmail(po.getOsoba().getEmail(),"Event Invitation from " + rezervisao.getIme() + " " + rezervisao.getPrezime(), s, rez.getId());
            }else{
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yy, HH:mm");
                String date = DATE_FORMAT.format(projekcija.getVreme());

                String listString = k.getSedista().toString();
                listString = listString.substring(1, listString.length()-1);

                String listString2 ="";

                for(Poziv pozz : pozivi){
                    listString2 += pozz.getOsoba().getIme() + " " + pozz.getOsoba().getPrezime() + ",";
                }
                listString2 = listString2.substring(0, listString2.length()-1);


                String s = "Event name: " + projekcija.getDogadjaj().getNaziv() + ", Place: " + projekcija.getSala().getUstanova().getNaziv()
                        + ", Auditorium: " + projekcija.getSala().getIme() + ", Date: " + date + ", Seats:" + listString + ", Persons:"
                         + listString2 + ", Total price: " + pozivi.get(0).getKarta().getPunaCena() + ",00 RSD";


                em.inviteEmail(po.getOsoba().getEmail(),"Reservation Detalis", s, rez.getId());
            }

        }

    }
}
