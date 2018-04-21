package com.isa.ISA;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

import javax.annotation.PostConstruct;

import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.*;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;*/
import org.springframework.stereotype.Component;

import com.isa.ISA.DTO.PolovanRekvDTO;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.RezervacijaRepository;
import com.isa.ISA.repository.SalaRepository;

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

    @Autowired
    private EncryptionService encService;

    @Autowired
    private RekvizitService rekvizitService;

    @Autowired
    private PozivRepository pozivRepo;

    @Autowired
    private KartaRepository kartaRepo;
    @PostConstruct
    public void initIt() throws NoSuchAlgorithmException {

        RegistrovaniKorisnik rk = new RegistrovaniKorisnik();
        rk.setUserName("pero");
        rk.setPassword("pero");
        rk.setIme("Pera");
        rk.setPrezime("Peric");
        rk.setEmail("pero@zahoo.com");
        rk.setStatus(StatusNaloga.AKTIVAN);
        List<Rezervacija> rrrr = new ArrayList<>();
        rk.setRezervacije(rrrr);
        List<Poziv> pppp = new ArrayList<>();
        rk.setPozivi(pppp);
        userService.addUser(rk);

        byte[] salt = encService.getNextSalt();
        byte[] newPass = encService.makeDigest(rk.getPassword(), salt);
        String pass = Arrays.toString(newPass);
        Encryption e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (userService.getUser(rk.getUserName()).getId() ));
        encService.addEncr(e);
        rk.setPassword(pass);

        /*String salt = KeyGenerators.string().generateKey();

        TextEncryptor encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        String textToEncrypt = rk.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        String encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption e = new Encryption(encryptedText, salt, rk.getId());
        encService.addEncr(e);
        rk.setPassword(encryptedText);*/
        System.out.println("Kreiran korisnik: " + rk.getUserName());
        userService.addUser(rk);

        RegistrovaniKorisnik rk2 = new RegistrovaniKorisnik();
        rk2.setUserName("mirko");
        rk2.setPassword("mirko");
        rk2.setEmail("indjic.tanja@gmail.com");
        rk2.setStatus(StatusNaloga.AKTIVAN);
        rk2.setBrojTelefona("4444");
        rk2.setGrad("a");
        rk2.setIme("Tanja");
        rk2.setPrezime("Mirkovic");
        userService.addUser(rk2);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(rk2.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (userService.getUser(rk2.getUserName()).getId() ));
        encService.addEncr(e);
        rk2.setPassword(pass);
       /* salt = KeyGenerators.string().generateKey();

        encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        textToEncrypt = rk2.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption e2 = new Encryption(encryptedText, salt, rk2.getId());
        encService.addEncr(e2);
        rk2.setPassword(encryptedText);*/
        System.out.println("Kreiran korisnik: " + rk2.getUserName());
        userService.addUser(rk2);

        RegistrovaniKorisnik rk3 = new RegistrovaniKorisnik();
        rk3.setUserName("f");
        rk3.setPassword("f");
        rk3.setEmail("mali.patuljko@gmail.com");
        rk3.setStatus(StatusNaloga.AKTIVAN);
        rk3.setBrojTelefona("4444");
        rk3.setGrad("f");
        rk3.setIme("f");
        rk3.setPrezime("f");
        userService.addUser(rk3);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(rk3.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (userService.getUser(rk3.getUserName()).getId() ));
        encService.addEncr(e);
        rk3.setPassword(pass);
        /*salt = KeyGenerators.string().generateKey();

        encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        textToEncrypt = rk3.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption e3 = new Encryption(encryptedText, salt, rk3.getId());
        encService.addEncr(e3);
        rk3.setPassword(encryptedText);*/
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
        userService.addUser(rk4);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(rk4.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID (userService.getUser(rk4.getUserName()).getId() );
        encService.addEncr(e);
        rk4.setPassword(pass);
        /*salt = KeyGenerators.string().generateKey();

        encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        textToEncrypt = rk4.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption e4 = new Encryption(encryptedText, salt, rk4.getId());
        encService.addEncr(e4);
        rk4.setPassword(encryptedText);*/



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
        a.setTip(TipAdmina.PRED);
        a.setEmail("admin@admin.com");
        a.setIme("a");
        a.setPrezime("a");
        a.setGrad("a");
        a.setBrojTelefona("333333333");
        a.setStatus(StatusNaloga.AKTIVAN);
        adminService.addAdmin(a);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(a.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (adminService.getAdmin(a.getUserName()).getId() ));
        encService.addEncr(e);
        a.setPassword(pass);

        /*salt = KeyGenerators.string().generateKey();

        encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        textToEncrypt = a.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption ea = new Encryption(encryptedText, salt, a.getId());
        encService.addEncr(ea);
        a.setPassword(encryptedText);*/
        System.out.println("Kreiran korisnik: " + a.getIme());
        adminService.addAdmin(a);

        Admin a2 = new Admin();
        a2.setUserName("admin2");
        a2.setPassword("admin2");
        a2.setTip(TipAdmina.POZBI);
        a2.setEmail("admin2");
        a2.setStatus(StatusNaloga.AKTIVAN);
        adminService.addAdmin(a2);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(a2.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (adminService.getAdmin(a2.getUserName()).getId() ));
        encService.addEncr(e);
        a2.setPassword(pass);
        /*salt = KeyGenerators.string().generateKey();

        encryptor = Encryptors.text("admin", salt);
        System.out.println("Salt: \"" + salt + "\"");

        textToEncrypt = a2.getPassword();
        System.out.println("Original text: \"" + textToEncrypt + "\"");

        encryptedText = encryptor.encrypt(textToEncrypt);
        System.out.println("Encrypted text: \"" + encryptedText + "\"");

        Encryption ea2 = new Encryption(encryptedText, salt, a2.getId());
        encService.addEncr(ea2);
        a2.setPassword(encryptedText);*/
        System.out.println("Kreiran korisnik: " + a2.getUserName());
        adminService.addAdmin(a2);

        Admin aa = new Admin();
        aa.setUserName("admin3");
        aa.setPassword("default");
        aa.setTip(TipAdmina.SYS);
        aa.setEmail("admin3");
        aa.setStatus(StatusNaloga.NERESEN);
        adminService.addAdmin(aa);

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(aa.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (adminService.getAdmin(aa.getUserName()).getId() ));
        encService.addEncr(e);
        aa.setPassword(pass);

        System.out.println("Kreiran korisnik: " + aa.getUserName());
        adminService.addAdmin(aa);

        PozoristeBioskop p1 = new PozoristeBioskop();
        p1.setBrojOcena(1);
        p1.setProsecnaOcena(3);
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
        p.setAktivna(true);
        p.setCena(300);
        p.setSala(s1);
        Calendar c = Calendar.getInstance(); // starts with today's date and time
        c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
        Date date = c.getTime();
        p.setVreme(date);
        p.setZauzetaSedista(new ArrayList<Sediste>());
        p.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj d = new Dogadjaj();
        d.setStatus(DogadjajStatus.CURRENT);
        d.setBrojOcena(0);
        d.setProsecnaOcena(0);
        d.setDonosiBodova(1);
        //   d.setGlumci(new ArrayList<Glumac>());
        d.setNaziv("Isa film 1");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.HOROR);
        d.setTrajanje(90);
        d.setSlika("5d2f9b0e-5fc2-4c47-8cd3-807e1dc99e42");

        dogadjajService.addDogadjaj2(d);

        p.setDogadjaj(d);
        p.setAktivna(true);
        projekcijaService.addProjekcija(p);



        projekcije.add(p);
        p1.setProjekcije(projekcije);

        d.setPrikazujeSe(projekcije);
        dogadjajService.addDogadjaj2(d);

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
        ArrayList<Poziv> pozivi = new ArrayList<>();
        Poziv poy = new Poziv();
        poy.setStatus(Status.PRIHVACENO);
        poy.setOsoba(rk3);
        poy.setPozvan(false);
        poy.setRezervacija(r);
        ArrayList<Sediste> zauzeta = new ArrayList<>();
        Karta ka = new Karta();
        ka.setPunaCena((int) p.getCena());
        ka.setPozoristeBioskop(p.getSala().getUstanova());
        ka.setSediste(p.getSala().getSedista().get(0));
        zauzeta.add(p.getSala().getSedista().get(0));
        ka.setVremeOdrzavanja(p.getVreme());
        kartaRepo.save(ka);
        poy.setKarta(ka);
        pozivRepo.save(poy);
        pozivi.add(poy);
        Poziv poyy = new Poziv();
        poyy.setStatus(Status.CEKA);
        poyy.setOsoba(rk2);
        poyy.setPozvan(true);
        poyy.setRezervacija(r);
        Karta kaa = new Karta();
        kaa.setPunaCena((int) p.getCena());
        kaa.setPozoristeBioskop(p.getSala().getUstanova());
        kaa.setSediste(p.getSala().getSedista().get(1));
        zauzeta.add(p.getSala().getSedista().get(1));
        kaa.setVremeOdrzavanja(p.getVreme());
        kartaRepo.save(kaa);
        poyy.setKarta(kaa);
        pozivRepo.save(poyy);
        pozivi.add(poyy);
        r.setUrezervaciji(pozivi);
        rezRepository.save(r);
        p.getRezervacije().add(r);
        p.setZauzetaSedista(zauzeta);
        rk3.setRezervacije(new ArrayList<Rezervacija>());
        rk3.getRezervacije().add(r);
        rk2.setRezervacije(new ArrayList<Rezervacija>());
        rk2.getRezervacije().add(r);
        rk3.setBodovi(1);
        userService.addUser(rk3);
        userService.addUser(rk2);
        projekcijaService.addProjekcija(p);

        Rezervacija r2 = new Rezervacija();
        r2.setRezervisao(rk2);
        r2.setPopust(0);
        r2.setProjekcija(p);
        rezRepository.save(r2);
        pozivi = new ArrayList<>();
        poy = new Poziv();
        poy.setStatus(Status.PRIHVACENO);
        poy.setOsoba(rk2);
        poy.setPozvan(false);
        poy.setRezervacija(r2);
        ka = new Karta();
        ka.setPunaCena((int) p.getCena());
        ka.setPozoristeBioskop(p.getSala().getUstanova());
        ka.setSediste(p.getSala().getSedista().get(3));
        zauzeta.add(p.getSala().getSedista().get(3));
        ka.setVremeOdrzavanja(p.getVreme());
        kartaRepo.save(ka);
        poy.setKarta(ka);
        pozivRepo.save(poy);
        pozivi.add(poy);
        r2.setUrezervaciji(pozivi);
        rezRepository.save(r2);
        p.setZauzetaSedista(zauzeta);
        p.getRezervacije().add(r2);
        rk2.getRezervacije().add(r2);
        rk2.setBodovi(2);
        userService.addUser(rk2);
        projekcijaService.addProjekcija(p);

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

        salt = encService.getNextSalt();
        newPass = encService.makeDigest(a3.getPassword(), salt);
        pass = Arrays.toString(newPass);
        System.out.println(pass);
        e = new Encryption();
        e.setSalt(salt);
        e.setEncryptedPass(newPass);
        e.setKorisnikID( (adminService.getAdmin(a3.getUserName()).getId() ));
        encService.addEncr(e);
        a3.setPassword(pass);
        adminService.addAdmin(a3);

        
        PolovanRekvDTO pr0 = new PolovanRekvDTO();
        pr0.setCena(124.42);
        pr0.setDatum(new java.sql.Date(2018, 4,27));
        pr0.setNaziv("first item");
        pr0.setOpis("item to sellllll");
        pr0.setSlika("assets/images/balkon.png");
        pr0.setUsername("zika");
        rekvizitService.addPolovan(pr0);
        
        PolovanRekvDTO pr1 = new PolovanRekvDTO();
        pr1.setCena(455.00);
        pr1.setDatum(new java.sql.Date(2018, 4,30));
        pr1.setNaziv("second item");
        pr1.setOpis("my second hand item");
        pr1.setSlika("assets/images/baby-with-dog.jpg");
        pr1.setUsername("zika");
        rekvizitService.addPolovan(pr1);
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
        s1.setIme("Vrh sala");
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
        p.setAktivna(true);
        p.setCena(300);
        p.setSala(s1);
        Calendar c = Calendar.getInstance(); // starts with today's date and time
        c.add(Calendar.DAY_OF_YEAR, 1);  // advances day by 2
        Date date = c.getTime();
        p.setVreme(date);
        p.setZauzetaSedista(new ArrayList<Sediste>());
        p.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj d = new Dogadjaj();
        d.setStatus(DogadjajStatus.CURRENT);
        d.setBrojOcena(0);
        d.setProsecnaOcena(0);
        d.setDonosiBodova(1);
        d.setNaziv("Web Film");
        d.setOpis("Akcioni film iz ise");
        d.setReziser("Minja");
        d.setZanr(Zanr.DRAMA);
        d.setSlika("e9a798ab-cbed-4348-bf05-4b09661e624a");
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
        pp.setAktivna(false);
        pp.setCena(300);
        pp.setSala(s1);
        pp.setVreme(new Date(System.currentTimeMillis()+24*60*60*1000));
        pp.setZauzetaSedista(new ArrayList<Sediste>());
        pp.setRezervacije(new ArrayList<Rezervacija>());
        Dogadjaj dd = new Dogadjaj();
        dd.setStatus(DogadjajStatus.CURRENT);
        dd.setBrojOcena(0);
        dd.setProsecnaOcena(0);
        dd.setDonosiBodova(1);
        dd.setNaziv("Web Film vol. 2");
        dd.setOpis("Akcioni film iz ise 2: Minja's revenge!");
        dd.setReziser("Minja");
        dd.setSlika("d0b0c187-9a31-430b-9dad-df78adc180d2");
        dd.setZanr(Zanr.HOROR);
        dd.setTrajanje(90);

        pp.setAktivna(false);

        dogadjajService.addDogadjaj2(dd);

        pp.setDogadjaj(dd);
        projekcijaService.addProjekcija(pp);

        projekcije.add(pp);
        p1.setProjekcije(projekcije);

        dd.setPrikazujeSe(projekcije);
        dogadjajService.addDogadjaj2(dd);

        pozoristeBioskopService.addPozoristeBioskop(p1);
        dd.setMestoOdrzavanja(p1);
        dogadjajService.updateDogadjaj2(dd);


    }

}