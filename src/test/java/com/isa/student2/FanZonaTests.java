package com.isa.student2;

import java.nio.charset.Charset;
import java.sql.Date;

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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.IsaApplication;
import com.isa.ISA.DTO.PolovanRekvDTO;
import com.isa.ISA.DTO.RekvizitDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class FanZonaTests {
	@Autowired
    MockMvc mockMvc;

    private RekvizitDTO rekvDTO;
    private PolovanRekvDTO polDTO;
    @Before
    public void setup(){
    	rekvDTO = new RekvizitDTO();
    	rekvDTO.setNaziv("test");
    	rekvDTO.setOpis("test-opis");
    	rekvDTO.setAdmin("admin");
    	rekvDTO.setCena(200.0);
    	rekvDTO.setSlika("");
    	rekvDTO.setPozBioID(1);

    	polDTO = new PolovanRekvDTO();
    	polDTO.setCena(200.0);
    	polDTO.setDatum(new Date(System.currentTimeMillis()));
    	polDTO.setNaziv("test");
    	polDTO.setOpis("test-opis");
    	polDTO.setSlika("");
    	polDTO.setUsername("pero");
    	
    }
    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void testDodajRekvizit() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/zvanicni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();
        String rez = mvcResult.getResponse().getContentAsString();
        Assert.assertNotEquals("", rez);
    }
    @Test
    public void testGetRekvizit() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/zvanicni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test = jsonArr.getJSONObject(0);
        String rez = test.getString("naziv");
        Assert.assertEquals("test", rez); 
    }
    @Test
    public void testUpdateRekvizit() throws Exception {
    	String v1 = rekvDTO.getNaziv();
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/zvanicni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();
        rekvDTO.setNaziv("test2");
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test = jsonArr.getJSONObject(0);
        int rez0 = test.getInt("id");
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/rekviziti/zvanicni/"+rez0)
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr0 = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test0 = jsonArr0.getJSONObject(0);
        String rez = test0.getString("naziv");
        Assert.assertNotEquals("test", rez); 
    }
    //Radi kad se pozove pojedinacno, inacee brlja
    @Test
    public void testDeleteRekvizit() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/zvanicni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test = jsonArr.getJSONObject(0);
        int rez0 = test.getInt("id");
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.delete("/rekviziti/zvanicni/"+rez0))
                .andReturn();
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr0 = new JSONArray(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(0, jsonArr0.length()); 
    }
    @Test
    public void testRezervacijaRekvizita() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/zvanicni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(rekvDTO)))
                .andReturn();
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test = jsonArr.getJSONObject(0);
        int id = test.getInt("id");
    	mvcResult = mockMvc.perform(
                 MockMvcRequestBuilders.get("/api/users")).andReturn();
    	JSONArray jsonArr0 = new JSONArray(mvcResult.getResponse().getContentAsString());
    	int userID = ((JSONObject)jsonArr0.get(0)).getInt("id");
        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/rekviziti/"+id+"/rezervisi/"+userID))
                .andReturn();

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rekviziti/zvanicni")).andReturn();
        JSONArray jsonArr1 = new JSONArray(mvcResult.getResponse().getContentAsString());
        JSONObject test0 = jsonArr1.getJSONObject(0);
        int rez = test0.getJSONArray("rezervacije").length();
        Assert.assertNotEquals(0, rez); 
    }
    
//POLOVNI REKVIZITI
    //za usera je postavljen pero
    
    @Test
    public void testDodajPolovan() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rekviziti/polovni")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(polDTO)))
                .andReturn();
        String rez = mvcResult.getResponse().getContentAsString();
        Assert.assertNotEquals("", rez);
    }    
    
}
