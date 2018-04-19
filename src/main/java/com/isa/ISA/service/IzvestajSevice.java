package com.isa.ISA.service;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.isa.ISA.DTO.PrimljenIzvestajDTO;
import com.isa.ISA.DTO.ReportDto;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.dodatno.ExcelUtil;
import com.isa.ISA.repository.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IzvestajSevice {

    @Autowired
    private PozoristeBioskopRepository pbRepository;

    @Autowired
    private DogadjajRepository dogadjajRepository;

    @Autowired
    private KartaRepository kartaRepository;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private RekvizitRepository rekvizitRepo;

    public PrimljenIzvestajDTO makeReport(ReportDto dto){

        PrimljenIzvestajDTO returnDTO = new PrimljenIzvestajDTO();
        List<String> oceneDogadjaja = new ArrayList<>();

        Admin admin = adminRepo.findOne(dto.getAdminId());
        PozoristeBioskop pb = pbRepository.findOne(dto.getPbId());
        List<Dogadjaj> dogadjaji = dogadjajRepository.findByMestoOdrzavanja(pb);

        boolean moze = false;

        for (PozoristeBioskop pozBi: admin.getMesta()) {
            if(pb.getId()==pozBi.getId())
                moze=true;
        }
    //    StringBuilder builder = new StringBuilder();
        if(moze){

            returnDTO.setOcenaPb("Average ambient rating for " + pb.getNaziv() + " is : " +pb.getProsecnaOcena());

         //   builder.append("Average ambient rating for " + pb.getNaziv() + " is : " + pb.getProsecnaOcena() + "<br/>");

            //float prosecnaOcenaAmbijenta = pb.getProsecnaOcena();
            for (Dogadjaj tempDog:dogadjaji) {
               oceneDogadjaja.add("Average rating for "+tempDog.getNaziv()+ " is : " +tempDog.getProsecnaOcena());
             //    builder.append("Average rating for "+tempDog.getNaziv()+ " is : " +tempDog.getProsecnaOcena() +"<br/>");
            }

            returnDTO.setOcenaDogadjaja(oceneDogadjaja);

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try{
                Date p = dateFormat.parse(dto.getFrom());
                Date k = dateFormat.parse(dto.getTo());
                int zaradaOdKarata = kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBetween(pb, p, k).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

                returnDTO.setZaradaOdKarata("Income from ticket sales is : " + zaradaOdKarata);


               // builder.append("Income from ticket sales is : " + zaradaOdKarata+"<br/>");

                List<ZvanicanRekvizit> rekviziti = rekvizitRepo.findByPreuzeti(pb);

                int zaradaOdRekvizita =0;

                for (ZvanicanRekvizit tempRekvizit:rekviziti) {
                    for(RezervacijaRekvizita tempRezervacija:tempRekvizit.getRezervacije()){
                        if(tempRezervacija.isReseno() && tempRezervacija.getDatum().after(p) && tempRezervacija.getDatum().before(k)){
                            zaradaOdRekvizita+=tempRekvizit.getCena();
                        }
                    }

                }
                returnDTO.setZaradaOdRekvizita("Income from props is : " + zaradaOdRekvizita);
                //builder.append("Income from props is : " + zaradaOdRekvizita+"<br/>");

                int ukupno = zaradaOdKarata+zaradaOdRekvizita;

                returnDTO.setUkupnaZarada("Full income for the given period is : " +ukupno);

             //   builder.append("Full income for the given period is : " +zaradaOdKarata+zaradaOdRekvizita+"<br/>");

               // returnDTO.setTekst(builder.toString());

                Map<Date,Integer> grafikPosetaDnevno = getPoseteOdDo(pb.getId(), p, k);
                Map<Integer, Integer> grafikPosetaNedeljno = new HashMap<>();
                Map<Integer, Integer> grafikPosetaMesecno = new HashMap<>();

                 Calendar c = Calendar.getInstance();

                for (Map.Entry<Date, Integer> entry : grafikPosetaDnevno.entrySet())
                {
                    c.setTime(entry.getKey());
                    int mesec = c.get(Calendar.MONTH);
                    int nedelja = c.get(c.WEEK_OF_YEAR);

                    Integer i = grafikPosetaNedeljno.get(nedelja);
                    if (i == null) {
                        grafikPosetaNedeljno.put(nedelja, entry.getValue());

                    }else{
                        grafikPosetaNedeljno.put(nedelja, i+entry.getValue());
                    }

                    Integer j = grafikPosetaMesecno.get(mesec);
                    if(i==null){
                        grafikPosetaMesecno.put(mesec, entry.getValue());
                    }else{
                        grafikPosetaMesecno.put(mesec, entry.getValue()+j);
                    }
                }

                c.setTime(p);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                returnDTO.setPrviDan(day);
                returnDTO.setMesec(month);
                returnDTO.setGodina(year);

                returnDTO.setGrafikPosetaDnevno(mapToList(grafikPosetaDnevno,p,k));
                returnDTO.setGrafikPosetaNedeljno(mapToListInt(grafikPosetaNedeljno,p,k));
                returnDTO.setGrafikPosetaMesecno(mapToListInt(grafikPosetaMesecno,p,k));




                return returnDTO;


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;







        }
        return null;


    }

    public float getOcenaAmbijenta(Long pb){
        return dogadjajRepository.findOne(pb).getProsecnaOcena();
    }

    public float getOcenaDogadjaj(Long dog){
        return dogadjajRepository.findOne(dog).getProsecnaOcena();
    }

    public int getPrihodIzmedju(Long pb, Date p, Date k){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
       // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBetween(pbi, p, k).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    public int getPrihodPre(Long pb, Date p){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
        // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBefore(pbi, p).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    public int getPrihodPosle(Long pb, Date p){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
        // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaAfter(pbi, p).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    private List<Integer> mapToList(Map<Date,Integer> grafik, Date prvi, Date poslednji){
        List<Integer> lista = new ArrayList<>();
        Map<Date, Integer> treeMap = new TreeMap<>(grafik);
        for (Date dTemp : treeMap.keySet()) {
          //  System.out.println(dTemp);
            lista.add(grafik.get(dTemp));
        }
        return lista;

    }


    private List<Integer> mapToListInt(Map<Integer,Integer> grafik, Date prvi, Date poslednji){
        List<Integer> lista = new ArrayList<>();
        Map<Integer, Integer> treeMap = new TreeMap<>(grafik);
        for (Integer dTemp : treeMap.keySet()) {
          //  System.out.println(dTemp);
            lista.add(grafik.get(dTemp));
        }
        return lista;

    }


    private Date addOneDay(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();

    }

    private Map<Date, Integer> addMissingDays(Map<Date, Integer> grafik, Date od, Date d2){
        Map<Date, Integer> sredjeno = new HashMap<>();
        Date odDan = setToZero(od);
        Date doDan = setToZero(d2);
        long diff = doDan.getTime() - odDan.getTime();
        long dani = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

      //  System.out.println(dani);

        Date trenutni = odDan;
        for(int i=0; i<dani; i++){
            trenutni = addOneDay(trenutni);
            Integer j = grafik.get(trenutni);
            if(j==null){
                grafik.put(trenutni, 0);
            }

        }

        Integer test = grafik.get(doDan);
        if(test==null){
            grafik.put(doDan, 0);
        }



        return sredjeno;
    }

    private Map<Date,Integer> getPosete(Long id, Date od, Date d2, int flag){ // Ovo bih mogao opaliti asihrono ili da salje na mail, to je jos bolje :D Tako za sve ove izvestaj
        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(id);
        Map<Date, Integer> grafik = new HashMap<>();
        List<Karta> karte = new ArrayList<>();

        if(flag==0)
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBetween(pbi, od, d2).forEach(karte::add);
        else if(flag==1)
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaAfter(pbi, od).forEach(karte::add);
        else
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBefore(pbi,d2).forEach(karte::add);


        for (Karta karta : karte) { // Generise mi mapu koja predstavlja grafik dana i broja prodatih karata
            Date temp = setToZero(karta.getVremeOdrzavanja());
            Integer i = grafik.get(temp);
            if(i==null){
                grafik.put(temp, 1);

            }else{
                grafik.put(temp, i+1);
            }

        }

        addMissingDays(grafik, od, d2);

        return grafik;

    }




    private Date setToZero(Date d){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    public Map<Date,Integer> getPoseteOdDo(Long id, Date od, Date d2){
        return getPosete(id, od, d2, 0);
    }

    public Map<Date,Integer> getPoseteOd(Long id, Date od){
        Date d2 = null;
        return getPosete(id, od, d2, 1);
    }

    public Map<Date,Integer> getPoseteDo(Long id, Date d2){
        Date od = null;
        return getPosete(id, od, d2, 2);
    }







}
