package com.mrsisa.mrsisaprojekat.S2.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.mrsisa.mrsisaprojekat.dto.EmployeeDTO;
import com.mrsisa.mrsisaprojekat.dto.RequestDTO;
import com.mrsisa.mrsisaprojekat.util.TestUtil;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class RequestIntegrationTest {
	private static final String URL_PREFIX = "/api/request";

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
    @WithMockUser(username = "sarajovic@gmail.com", roles = {"PHARMACY_ADMIN"})
    public void rejectRequest() throws Exception {
    	RequestDTO request = new RequestDTO();
    	request.setAccepted(true);
    	request.setRejectionReason("");
    	EmployeeDTO employee = new EmployeeDTO();
    	employee.setEmail("svetozartodorovic@gmail.com");
    	request.setEmployee(employee);
    	request.setId(2L);
    	String reqJson = TestUtil.json(request);
    	
        mockMvc.perform(put(URL_PREFIX+"/update/2").contentType(contentType).content(reqJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.employee.email").value("svetozartodorovic@gmail.com"));
                
    }

}
