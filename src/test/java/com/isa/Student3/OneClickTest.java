package com.isa.Student3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.OcenaDTO;
import com.isa.ISA.DTO.OneClickDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.IsaApplication;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
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
public class OneClickTest {

    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private static int id = 6;


    @Autowired
    MockMvc mockMvc;

    private OneClickDTO o;


    @Before
    public void setUp(){

        o = new OneClickDTO();
        o.setBpId(1l);
        o.setBrojRedova(10);
        o.setBrojSedista(10);
        o.setCena(99);
        //   o.setSedista();


        List<SedisteDTO> sedista = new ArrayList<>();
        for(int i =0; i<=99; i++){
            SedisteDTO sediste = new SedisteDTO();
            sediste.setId(i);
            if(i>3){
                sediste.setChecked(false);
            }else {
                sediste.setChecked(true);
            }
            sediste.setType("REGULAR");
            sedista.add(sediste);
        }

        o.setSedista(sedista);

    }



    @Test
    public void atestDodajZauzeto() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/projekcija/zauzmi/1")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
                        )).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void btestUserDobij() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/quickRezervacije/1")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1))).andReturn();

    }

    @Test
    public void ctestUserZauzmi() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/oneClick/3/user/1")).andExpect(status().isOk()).andReturn();
        JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
        String odgovor = result.getString("message");
        Assert.assertEquals("SUCCESS", odgovor);

    }

    @Test
    public void dtestUserZauzmiFail() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/oneClick/3/user/1")).andExpect(status().isOk()).andReturn();
        JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
        String odgovor = result.getString("message");
        Assert.assertEquals("ERROR", odgovor);

    }




}
