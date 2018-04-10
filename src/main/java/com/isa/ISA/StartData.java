package com.isa.ISA;

import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.*;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.SalaRepository;
import com.isa.ISA.repository.SedisteRepository;
import com.isa.ISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StartData {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private SedisteRepository sedisteRepository; // mrzi me da pravim servis sada

    @Autowired
    private SalaRepository salaRepository; //dito


    @Autowired
    private PozoristeBioskopService pbService;

    @Autowired
    private DogadjajService dogadjajService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private PozoristeBioskopService pozoristeBioskopService;

    @PostConstruct
    public void initIt(){

        RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
        rk.setUserName("kor");
        rk.setPassword("kor");
        rk.setEmail("kor");
        rk.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + rk.getUserName());
        userService.addUser(rk);

        Admin a = new Admin();
        a.setUserName("admin");
        a.setPassword("admin");
        a.setTip(TipAdmina.SYS);
        a.setEmail("admin");
        a.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + a.getUserName());
        adminService.addAdmin(a);

        PozoristeBioskop p1 = new PozoristeBioskop();
        p1.setBrojOcena(0);
        p1.setProsecnaOcena(0);
        p1.setAdresa("Centar centra 1");
        p1.setBronzePopust(5);
        p1.setSilverPopust(10);
        p1.setGoldPopust(15);
        p1.setBronzeTreshold(10);
        p1.setSilverTreshold(20);
        p1.setGoldTreshold(30);
        List<Admin> adminList = new ArrayList<>();
        adminList.add(a);
        p1.setAdmini(adminList);
        p1.setNaziv("Moje prvo pozoriste");
        p1.setOpis("Prvo kreirano pozoriste");
        p1.setTip(TipUstanove.POZORISTE);
        p1.setUrlMape("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2361.7969083507946!2d19.842332146552927!3d45.254552686290445!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x475b1069a2052523%3A0xca354a229e89ab6d!2z0J_QvtC30L7RgNC40YjQvdC4INC80YPQt9C10Zgg0JLQvtGY0LLQvtC00LjQvdC1!5e0!3m2!1ssr!2srs!4v1522785124974");
        Sala s1 = new Sala();
        s1.setBrRed(10);
        s1.setBrSedista(10);
        s1.setIme("Prva sala u pozoristu");
       // s1.setUstanova(p1);
        List<Sala> sale = new ArrayList<>();


        List<Sediste> sedista = new ArrayList<>();
        for(int i =0; i<=99; i++){
            Sediste sediste = new Sediste();
            sediste.setBroj(i%10);
            sediste.setRed( Math.floorDiv(i, 10));
            sediste.setTipSedista(TipSedista.OBICNO);
            sedisteRepository.save(sediste);
            sedista.add(sediste);
        }

        s1.setSedista(sedista);
        salaRepository.save(s1);
        sale.add(s1);
        p1.setSale(sale);

        List<Projekcija> projekcije = new ArrayList<>();
        Projekcija p = new Projekcija();
        p.setCena(300);
        p.setSala(s1);
        p.setVreme(new Date());
        p.setZauzetaSedista(new ArrayList<Sediste>());
        p.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj d = new Dogadjaj();
        d.setBrojOcena(0);
        d.setProsecnaOcena(0);
        d.setDonosiBodova(1);
        d.setGlumci(new ArrayList<Glumac>());
        d.setNaziv("Isa film 1");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.HOROR);
        d.setTrajanje(90);

        dogadjajService.addDogadjaj(d);

        p.setDogadjaj(d);
        projekcijaService.addProjekcija(p);

        projekcije.add(p);
        p1.setProjekcije(projekcije);

        pozoristeBioskopService.addPozoristeBioskop(p1);

        PozoristeBioskop pb2 = new PozoristeBioskop();
        pb2.setId(1);
        s1.setUstanova(pb2);
        salaRepository.save(s1);
        addBioskop(a);


    }

    private void addBioskop(Admin a){
        PozoristeBioskop p1 = new PozoristeBioskop();
        p1.setBrojOcena(1);
        p1.setProsecnaOcena(5);
        p1.setAdresa("Zmaj Jovina 12");
        p1.setBronzePopust(5);
        p1.setSilverPopust(10);
        p1.setGoldPopust(15);
        p1.setBronzeTreshold(10);
        p1.setSilverTreshold(20);
        p1.setGoldTreshold(30);
        List<Admin> adminList = new ArrayList<>();
        adminList.add(a);
        p1.setAdmini(adminList);
        p1.setNaziv("Arena Cineplex");
        p1.setOpis("Bioskop 101");
        p1.setTip(TipUstanove.BIOSKOP);
        p1.setUrlMape("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2361.7969083507946!2d19.842332146552927!3d45.254552686290445!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x475b1069a2052523%3A0xca354a229e89ab6d!2z0J_QvtC30L7RgNC40YjQvdC4INC80YPQt9C10Zgg0JLQvtGY0LLQvtC00LjQvdC1!5e0!3m2!1ssr!2srs!4v1522785124974");
        Sala s1 = new Sala();
        s1.setBrRed(10);
        s1.setBrSedista(10);
        s1.setIme("Prva sala u pozoristu");
        // s1.setUstanova(p1);
        List<Sala> sale = new ArrayList<>();


        List<Sediste> sedista = new ArrayList<>();
        for(int i =0; i<=99; i++){
            Sediste sediste = new Sediste();
            sediste.setBroj(i%10);
            sediste.setRed( Math.floorDiv(i, 10));
            sediste.setTipSedista(TipSedista.OBICNO);
            sedisteRepository.save(sediste);
            sedista.add(sediste);
        }

        s1.setSedista(sedista);
        salaRepository.save(s1);
        sale.add(s1);
        p1.setSale(sale);

        List<Projekcija> projekcije = new ArrayList<>();
        Projekcija p = new Projekcija();
        p.setCena(300);
        p.setSala(s1);
        p.setVreme(new Date());
        p.setZauzetaSedista(new ArrayList<Sediste>());
        p.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj d = new Dogadjaj();
        d.setBrojOcena(0);
        d.setProsecnaOcena(0);
        d.setDonosiBodova(1);
        d.setGlumci(new ArrayList<Glumac>());
        d.setNaziv("Web Film");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.DRAMA);
        d.setTrajanje(90);

        dogadjajService.addDogadjaj(d);

        p.setDogadjaj(d);
        projekcijaService.addProjekcija(p);

        projekcije.add(p);
        p1.setProjekcije(projekcije);

        pozoristeBioskopService.addPozoristeBioskop(p1);

        PozoristeBioskop pb2 = new PozoristeBioskop();
        pb2.setId(1);
        s1.setUstanova(pb2);
        salaRepository.save(s1);


    }

}
