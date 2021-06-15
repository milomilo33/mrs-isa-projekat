package com.mrsisa.mrsisaprojekat.S4.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mrsisa.mrsisaprojekat.model.IssuanceMode;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.MedicamentForm;
import com.mrsisa.mrsisaprojekat.util.TestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class MedicamentIntegrationTest {

	private static final String URL_PREFIX = "/api/medicaments";
	
	private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                        .apply(springSecurity())
                                        .build();
    }
    
    @Test
    @WithMockUser(username = "milicamaric@gmail.com", roles = {"SYSTEM_ADMIN"})
    public void testGetUnvalidMedicament() throws Exception{
    	mockMvc.perform(get(URL_PREFIX + "/100"))
    			.andExpect(status().isNotFound());
    }
    
    @Test
    @WithMockUser(username = "milicamaric@gmail.com", roles = {"SYSTEM_ADMIN"})
    public void testGetValidMedicament() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + "/1"))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(contentType))
    			.andExpect(jsonPath("$.id").value(1))
    			.andExpect(jsonPath("$.type").value("Humani lekovi"));
    }
    
    @Test
    @WithMockUser(username = "milicamaric@gmail.com", roles = {"SYSTEM_ADMIN"})
    public void testMedicamentUpdate() throws Exception{
    	Medicament dto = new Medicament();
    	dto.setId(1L);
    	dto.setAnnotations("neke napomene");
    	dto.setPoints(2);
    	dto.setType("Humani lek");
    	dto.setManufacturer("Galenika");
    	dto.setName("Brufen");
    	dto.setIssuanceMode(IssuanceMode.WITHOUTPRESCRIPTION);
    	dto.setMedicamentForm(MedicamentForm.CAPSULE);
    	dto.setStructure("Sastav leka");
    	dto.setSubstituteMedicaments(null);
    	
    	String reqJson = TestUtil.json(dto);
    	mockMvc.perform(post(URL_PREFIX + "/updateMedicament").contentType(contentType).content(reqJson))
    			.andExpect(status().isOk());
    } 
    
    @Test
    @WithMockUser(username = "milicamaric@gmail.com", roles = {"SYSTEM_ADMIN"})
    public void testFailedMedicamentUpdate() throws Exception{
    	Medicament dto = new Medicament();
    	dto.setId(100L);
    	dto.setAnnotations("neke napomene");
    	dto.setPoints(2);
    	dto.setType("Humani lek");
    	dto.setManufacturer("Galenika");
    	dto.setName("Brufen");
    	dto.setIssuanceMode(IssuanceMode.WITHOUTPRESCRIPTION);
    	dto.setMedicamentForm(MedicamentForm.CAPSULE);
    	dto.setStructure("Sastav leka");
    	dto.setSubstituteMedicaments(null);
    	
    	String reqJson = TestUtil.json(dto);
    	mockMvc.perform(post(URL_PREFIX + "/updateMedicament").contentType(contentType).content(reqJson))
    			.andExpect(status().isBadRequest());
    }
}
