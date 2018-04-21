package com.isa.Student3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.OcenaDTO;
import com.isa.ISA.IsaApplication;
import org.hamcrest.Matchers;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class OcenjivanjeTest {

    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private static int id = 1;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testOceni() throws Exception {

        OcenaDTO o = new OcenaDTO();
        o.setOcenaAmbijenta(5);
        o.setOcenaDogadjaja(5);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/pozivi/oceni/1")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(o)
        )).andExpect(status().isOk()).andReturn(); // Ako se ne menja startdata

        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/d/"+id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.prosecnaOcena", Matchers.is(5.0))).andReturn();// Ako se ne menja startdata

        MvcResult mvcResult3 = mockMvc.perform(
                MockMvcRequestBuilders.get("/pb/"+id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.prosecnaOcena", Matchers.is(4.0))).andReturn(); // Ocigledno radi sta treba :D

    }


}
