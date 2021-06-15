package com.mrsisa.mrsisaprojekat.S3.integration;

import com.mrsisa.mrsisaprojekat.dto.AppointmentWithStringDatesDTO;
import com.mrsisa.mrsisaprojekat.util.TestUtil;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class DermatologistIntegrationTest {
    private static final String URL_PREFIX = "/api/dermatologist";

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
    @WithMockUser(username = "aleksandarstevanovic@gmail.com", roles = {"DERMATOLOGIST"})
    public void testCreateAndScheduleNewExamination() throws Exception {
        AppointmentWithStringDatesDTO dto = new AppointmentWithStringDatesDTO();
        dto.setPatientEmail("lukamarkovic@gmail.com");
        dto.setDate("2021-07-07");
        dto.setTermFrom("10:55:00");
        dto.setTermTo("11:55:00");

        String reqJson = TestUtil.json(dto);

        // zapocinjanje pregleda
        mockMvc.perform(get("/api/appointments/19/start"));

        mockMvc.perform(post(URL_PREFIX + "/appointments/schedule-new/7").contentType(contentType).content(reqJson))
                .andExpect(status().isOk());
    }
}
