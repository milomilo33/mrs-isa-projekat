package com.mrsisa.mrsisaprojekat.S3.integration;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class EPrescriptionIntegrationTest {
    private static final String URL_PREFIX = "/api/eprescriptions";

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
    @WithMockUser(username = "svetozartodorovic@gmail.com", roles = {"PHARMACIST"})
    public void testDispenseMedication() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/2/dispense"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "nebitno", roles = {"PHARMACIST"})
    public void testPrescriptionMedicationForMedicalReport() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/medical-report/1/prescription-medicaments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].medicament.id").value(1))
                .andExpect(jsonPath("$[0].quantity").value(200));
    }
}
