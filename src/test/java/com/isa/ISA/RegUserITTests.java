package com.isa.ISA;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.DTO.RezervacijaDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class RegUserITTests {
    @Autowired
    MockMvc mockMvc;

    private RegKorDTO loginUser;
    private RegKorDTO registerUser;
    private RezervacijaDTO newRez;
    @Before
    public void setup(){
        registerUser = new RegKorDTO();
        registerUser.setUserName("test");
        registerUser.setPassword("test");
        registerUser.setIme("test");
        registerUser.setPrezime("test");
        registerUser.setGrad("test");
        registerUser.setBrojTelefona("test");
        registerUser.setEmail("tanja_indjic@yahoo.com");

        loginUser = new RegKorDTO();
        loginUser.setPassword(registerUser.getPassword());
        loginUser.setUserName(registerUser.getUserName());

        newRez = new RezervacijaDTO();
        newRez.setPopust(10);
        newRez.setPozvani(Arrays.asList(Long.valueOf(2),Long.valueOf(4)));
        newRez.setRezervisao(Long.valueOf(3));
        newRez.setProjekcija(Long.valueOf(1));
        newRez.setSedista(Arrays.asList(Long.valueOf(4),Long.valueOf(9),(Long.valueOf(1)) ,Long.valueOf(7)) );

    }
    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void login1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/register")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(registerUser)))
                .andReturn();
        String l = mvcResult.getResponse().getContentAsString();
        System.out.println("REGISTER:");
        System.out.println(l);
        Assert.assertEquals("", l);

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(loginUser)))
                .andReturn();
        l = mvcResult.getResponse().getContentAsString();
        System.out.println("LOGIN1:");
        System.out.println(l);
        Assert.assertEquals("", l);
    }

    @Test
    public void login2() throws Exception {
        //ODGOVOR NA MAIL
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/register/" + registerUser.getEmail())).andReturn();

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(loginUser)))
                .andReturn();
        String l = mvcResult.getResponse().getContentAsString();
        System.out.println("LOGIN2:");
        System.out.println(l);
/*		ObjectMapper mapper = new ObjectMapper();
		List<Poziv> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Poziv>>() {});*/

        //TREBA DA VRATI KORISNIKA SAD
        Assert.assertTrue(l.contains("\"userName\":\"test\""));
    }

    @Test
    public void settings() throws Exception {
        registerUser.setBrojTelefona("222333");
        registerUser.setIme("Branko");
        registerUser.setPrezime("Kockica");
        registerUser.setPassword("novipass");

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/settings/reg")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(registerUser)))
                .andReturn();
        String l = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("",l);

        loginUser.setPassword(registerUser.getPassword());


        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(loginUser)))
                .andReturn();

        l = mvcResult.getResponse().getContentAsString();
        System.out.println("LOGIN3:");
        System.out.println(l);

        Assert.assertTrue(l.contains("\"ime\":\"Branko\""));


    }

    @Test
    public void reserveAndAccept() throws Exception{
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/test")).andReturn();
        JSONObject jsonObj = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONArray array = jsonObj.getJSONArray("rezervacije");
        Long id = jsonObj.getLong("id");
        newRez.setRezervisao(id);
        System.out.println("RESERVATIONS BEFORE:");
        System.out.println(array.length());
        int pre = array.length();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/reserve")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(newRez)))
                .andReturn();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/test")).andReturn();
        jsonObj = new JSONObject(mvcResult.getResponse().getContentAsString());
        array = jsonObj.getJSONArray("rezervacije");
        System.out.println("RESERVATIONS AFTER:");

        System.out.println(array.length());
        Assert.assertNotEquals(pre, array.length());
        int posleRez = array.length();


    }
    @Test
    public void searchAndAddFriend() throws Exception{
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/settings/search")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString("pera.")))
                .andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        System.out.println(res);
        Assert.assertNotEquals("",res);


    }


    @Test
    public void acceptReservation() throws Exception{

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/pero")).andReturn();

        JSONObject jsonObj = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONArray array = jsonObj.getJSONArray("rezervacije");
        Long id = jsonObj.getLong("id");

        newRez.setPozvani(Arrays.asList(Long.valueOf(id)));
        System.out.println("RESERVATIONS BEFORE:");
        System.out.println(array.length());
        int pre = array.length();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/reserve")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(newRez)))
                .andReturn();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/pero")).andReturn();
        jsonObj = new JSONObject(mvcResult.getResponse().getContentAsString());
        array = jsonObj.getJSONArray("rezervacije");
        System.out.println("RESERVATIONS AFTER INVITE:");

        System.out.println(array.length());
        Assert.assertNotEquals(pre, array.length());

    }

}
