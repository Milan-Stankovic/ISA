package com.isa.Student3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.ProjekcijaDTO;
import com.isa.ISA.DTO.ReportDto;
import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.IsaApplication;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IsaApplication.class
)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(locations = "classpath:application.properties")
public class PbAdminTest {

    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private static int id = 1;


    @Autowired
    MockMvc mockMvc;

    @Test
    public void atestAddSala() throws Exception {


        SalaDTO o = new SalaDTO();
        o.setBrRed(1);
        o.setBrSedista(1);
        o.setIme("TEST SALA");
        o.setUstanova(1l);
        SedisteDTO sed = new SedisteDTO();
        sed.setChecked(true);
        sed.setType("VIP");
        sed.setId(0);
        List<SedisteDTO> sedista = new ArrayList<>();
        sedista.add(sed);
        o.setSedista(sedista);


        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/sala/add")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
                        )).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/sala/4")
                        ).andExpect(status().isOk()).andExpect(jsonPath("$.ime", Matchers.is("TEST SALA"))).andReturn();


    }


    @Test
    public void ctestAddProjekcija() throws Exception {


        ProjekcijaDTO o = new ProjekcijaDTO();
        o.setAktivna(true);
        o.setCena(550);

        o.setDate("06-04-1995 04:35:07");
        o.setDogadjaj(1l);
        o.setUstanova(1l);
        o.setSala(1l);


        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/d/projekcija")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
                        )).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/projekcija/6")
        ).andExpect(status().isOk()).andExpect(jsonPath("$.cena", Matchers.is(550.0))).andReturn();


    }



    @Test
    public void btestAddSalaFail() throws Exception {


        SalaDTO o = new SalaDTO();
        o.setBrRed(1);
        o.setBrSedista(1);
        o.setIme("TEST SALA");
        o.setUstanova(1l);
        SedisteDTO sed = new SedisteDTO();
        sed.setChecked(true);
        sed.setType("VIP");
        sed.setId(0);
        List<SedisteDTO> sedista = new ArrayList<>();
        //sedista.add(sed);
        o.setSedista(sedista);


        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/sala/add")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
                        )).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/sala/5")
        ).andExpect(status().isOk()).andExpect(jsonPath("$.ime").doesNotExist()).andReturn();

    }


    @Test
    public void ftestIzvestaj() throws Exception {

        ReportDto o = new ReportDto();
        o.setAdminId(6l);
        o.setFrom("06-03-2018 04:35:07");
        o.setTo("20-03-2018 04:35:07");
        o.setPbId(1l);


        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/fullIzvestaj")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
                        )).andExpect(status().isOk()).andReturn();


        JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
        String odgovor = result.getString("ukupnaZarada");
        Assert.assertEquals("Full income for the given period is : 0", odgovor);

        String odgovor2 = result.getString("ocenaPb");
        Assert.assertEquals("Average ambient rating for Moje prvo pozoriste is : 3.0", odgovor2);



    }




}
