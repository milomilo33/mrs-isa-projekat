package com.mrsisa.mrsisaprojekat.S1.integration;
import com.mrsisa.mrsisaprojekat.dto.AddAllergyDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class IntegrationsTests {
    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "anasimic@gmail.com", roles = {"PATIENT"})
    public void reserveExaminationTest() throws Exception {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1L);
        appointmentDTO.setPatientEmail("anasimic@gmail.com");
        String reqJson = TestUtil.json(appointmentDTO);

        mockMvc.perform(post("/api/patients/reserve_appointment")
                .contentType(contentType).content(reqJson))
                .andExpect(status().isCreated());
    }

    @Test
    @Rollback
    @Transactional
    @WithMockUser(username = "anasimic@gmail.com", roles = {"PATIENT"})
    public void removeAllergyTest() throws Exception {
        AddAllergyDTO addAllergyDTO = new AddAllergyDTO();
        addAllergyDTO.setPatientEmail("anasimic@gmail.com");
        addAllergyDTO.setMedicamentId(2L);
        String reqJson = TestUtil.json(addAllergyDTO);

        mockMvc.perform(post("/api/patients/remove_allergy")
                .contentType(contentType).content(reqJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "anasimic@gmail.com", roles = {"PATIENT"})
    public void getAllergiesTest() throws Exception {
        mockMvc.perform(get("/api/patients/get_allergies/anasimic@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2));
    }
}
