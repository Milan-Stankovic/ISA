package com.isa.ISA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.isa.ISA.repository.RezervacijaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.ISA.DTO.RekvizitDTO;
import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Glumac;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.RezervacijaRekvizita;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.enums.StatusPrijateljstva;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.dbModel.enums.Zanr;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.SalaRepository;
import com.isa.ISA.repository.SedisteRepository;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.DogadjajService;
import com.isa.ISA.service.PozoristeBioskopService;
import com.isa.ISA.service.PrijateljService;
import com.isa.ISA.service.ProjekcijaService;
import com.isa.ISA.service.RekvizitService;
import com.isa.ISA.service.UserService;

@Component
public class StartData {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private PrijateljService prijateljService;


    @Autowired
    private SalaRepository salaRepository; //dito

    @Autowired
    private RezervacijaRepository rezRepository; //dito

    @Autowired
    private PozoristeBioskopService pbService;

    @Autowired
    private DogadjajService dogadjajService;

    @Autowired
    private PozoristeBioskopService pozoristeBioskopService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @PostConstruct
    public void initIt(){

        RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
        rk.setUserName("pero");
        rk.setPassword("pero");
        rk.setIme("Pera");
        rk.setPrezime("Peric");
        rk.setEmail("pero@zahoo.com");
        rk.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + rk.getUserName());
        userService.addUser(rk);

        RegistrovaniKorisnik rk2 = new RegistrovaniKorisnik();
        rk2.setUserName("mirko");
        rk2.setPassword("mirko");
        rk2.setEmail("mirko@gmail.com");
        rk2.setStatus(StatusNaloga.AKTIVAN);
        rk2.setBrojTelefona("4444");
        rk2.setGrad("a");
        rk2.setIme("Mirko");
        rk2.setPrezime("Mirkovic");
        System.out.println("Kreiran korisnik: " + rk2.getUserName());
        userService.addUser(rk2);

        RegistrovaniKorisnik rk3 = new RegistrovaniKorisnik();
        rk3.setUserName("f");
        rk3.setPassword("f");
        rk3.setEmail("f@gmail.com");
        rk3.setStatus(StatusNaloga.AKTIVAN);
        rk3.setBrojTelefona("4444");
        rk3.setGrad("f");
        rk3.setIme("f");
        rk3.setPrezime("f");
        System.out.println("Kreiran korisnik: " + rk3.getUserName());
        userService.addUser(rk3);


        RegistrovaniKorisnik rk4 = new RegistrovaniKorisnik();
        rk4.setUserName("zika");
        rk4.setPassword("zika");
        rk4.setEmail("zika@gmail.com");
        rk4.setStatus(StatusNaloga.AKTIVAN);
        rk4.setBrojTelefona("4444");
        rk4.setGrad("zika");
        rk4.setIme("Zika");
        rk4.setPrezime("Zikic");


        System.out.println("Kreiran korisnik: " + rk4.getUserName());
        userService.addUser(rk4);

        Prijatelj p0 = new Prijatelj();
        p0.setPosiljalac(userService.getUser(rk.getUserName()));
        p0.setPrimalac(userService.getUser(rk3.getUserName()));
        p0.setStatus(StatusPrijateljstva.PRIHVACENO);
        prijateljService.addFriendship(p0);

        Prijatelj p2 = new Prijatelj();
        p2.setPosiljalac(userService.getUser(rk3.getUserName()));
        p2.setPrimalac(userService.getUser(rk2.getUserName()));
        p2.setStatus(StatusPrijateljstva.PRIHVACENO);
        prijateljService.addFriendship(p2);

        Prijatelj p3 = new Prijatelj();
        p3.setPosiljalac(userService.getUser(rk4.getUserName()));
        p3.setPrimalac(userService.getUser(rk3.getUserName()));
        p3.setStatus(StatusPrijateljstva.POSLATO);
        prijateljService.addFriendship(p3);

        Admin a = new Admin();
        a.setUserName("admin");
        a.setPassword("admin");
        a.setTip(TipAdmina.SYS);
        a.setEmail("admin");
        a.setIme("a");
        a.setPrezime("a");
        a.setGrad("a");
        a.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + a.getIme());
        adminService.addAdmin(a);

        Admin a2 = new Admin();
        a2.setUserName("admin2");
        a2.setPassword("default");
        a2.setTip(TipAdmina.POZBI);
        a2.setEmail("admin2");
        a2.setStatus(StatusNaloga.AKTIVAN);
        System.out.println("Kreiran korisnik: " + a2.getUserName());
        adminService.addAdmin(a2);


        PozoristeBioskop p1 = new PozoristeBioskop();
        p1.setBrojOcena(0);
        p1.setProsecnaOcena(0);
        p1.setAdresa("Centar centra 1");
        List<Admin> adminList = new ArrayList<>();
        adminList.add(a2);
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
            sediste.setTipSedista(TipSedista.REGULAR);
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
        //   d.setGlumci(new ArrayList<Glumac>());
        d.setNaziv("Isa film 1");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.HOROR);
        d.setTrajanje(90);

        dogadjajService.addDogadjaj2(d);

        p.setDogadjaj(d);
        projekcijaService.addProjekcija(p);

        projekcije.add(p);
        p1.setProjekcije(projekcije);

        pozoristeBioskopService.addPozoristeBioskop(p1);
        d.setMestoOdrzavanja(p1);
        dogadjajService.updateDogadjaj2(d);

        a2.setMesta(new ArrayList<PozoristeBioskop>());
        a2.getMesta().add(p1);


        PozoristeBioskop pb2 = new PozoristeBioskop();
        pb2.setId(1);
        s1.setUstanova(pb2);
        salaRepository.save(s1);
        addBioskop(a2, 1);
        addBioskop(a2, 2);

        Rezervacija r = new Rezervacija();
        r.setRezervisao(rk3);
        r.setPopust(0);
        r.setProjekcija(p);
        rezRepository.save(r);

        rk3.setRezervacije(new ArrayList<Rezervacija>());
        rk3.getRezervacije().add(r);
        userService.addUser(rk3);

        Admin a3 = new Admin();
        a3.setUserName("fanadmin");
        a3.setPassword("fanadmin");
        a3.setTip(TipAdmina.FAN);
        a3.setEmail("adm@fan.com");
        a3.setStatus(StatusNaloga.AKTIVAN);
        ArrayList<PozoristeBioskop> pppppp = new ArrayList<PozoristeBioskop>();
        pppppp.add(p1);
        a3.setMesta(pppppp);
        //   System.out.println("Kreiran korisnik: " + a3.getUserName());
        adminService.addAdmin(a3);

    }

    private void addBioskop(Admin a, int koji){
        PozoristeBioskop p1 = new PozoristeBioskop();
        p1.setBrojOcena(1);
        p1.setProsecnaOcena(5);
        p1.setAdresa("Zmaj Jovina 12");
        List<Admin> adminList = new ArrayList<>();
        adminList.add(a);
        p1.setAdmini(adminList);
        if(koji == 1)
            p1.setNaziv("Arena Cineplex");
        else
            p1.setNaziv("Cinestar");
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
            sediste.setTipSedista(TipSedista.REGULAR);
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
        d.setNaziv("Web Film");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.DRAMA);
        d.setTrajanje(90);

        dogadjajService.addDogadjaj2(d);

        p.setDogadjaj(d);
        projekcijaService.addProjekcija(p);

        projekcije.add(p);
        p1.setProjekcije(projekcije);

        pozoristeBioskopService.addPozoristeBioskop(p1);
        d.setMestoOdrzavanja(p1);
        dogadjajService.updateDogadjaj2(d);

        a.getMesta().add(p1);
        adminService.updateAdmin(a);

        PozoristeBioskop pb2 = new PozoristeBioskop();
        pb2.setId(1);
        s1.setUstanova(pb2);
        salaRepository.save(s1);


        Projekcija pp = new Projekcija();
        pp.setCena(300);
        pp.setSala(s1);
        pp.setVreme(new Date(System.currentTimeMillis()+24*60*60*1000));
        pp.setZauzetaSedista(new ArrayList<Sediste>());
        pp.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj dd = new Dogadjaj();
        dd.setBrojOcena(0);
        dd.setProsecnaOcena(0);
        dd.setDonosiBodova(1);
        dd.setNaziv("Web Film vol. 2");
        dd.setOpis("Akcioni film iz ise 2: Minja's revenge!");
        dd.setReziser("Minja");
        dd.setZanr(Zanr.HOROR);
        dd.setTrajanje(90);

        dogadjajService.addDogadjaj2(dd);

        pp.setDogadjaj(dd);
        projekcijaService.addProjekcija(pp);

        projekcije.add(pp);
        p1.setProjekcije(projekcije);

        pozoristeBioskopService.addPozoristeBioskop(p1);
        dd.setMestoOdrzavanja(p1);
        dogadjajService.updateDogadjaj2(dd);


    }

}