package com.sunbird.serve.volunteering;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.ContactDetails;
import com.sunbird.serve.volunteering.models.request.IdentityDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class VolunteeringApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // This test ensures the application context loads successfully
    }

    @Test
    void healthCheck_ShouldReturnOk() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        mockMvc.perform(get("/api/v1/serve-volunteering/actuator/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_WithValidRequest_ShouldReturnCreated() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        UserRequest userRequest = createValidUserRequest();

        mockMvc.perform(post("/api/v1/serve-volunteering/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        UserRequest invalidRequest = createInvalidUserRequest();

        mockMvc.perform(post("/api/v1/serve-volunteering/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    private UserRequest createValidUserRequest() {
        UserRequest request = new UserRequest();
        request.setRole(Arrays.asList("Volunteer"));
        request.setAgencyId("test-agency");
        request.setStatus("Active");
        
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail("test@example.com");
        contactDetails.setMobile("1234567890");
        request.setContactDetails(contactDetails);
        
        IdentityDetails identityDetails = new IdentityDetails();
        identityDetails.setFullname("Test User");
        identityDetails.setGender("Male");
        request.setIdentityDetails(identityDetails);
        
        return request;
    }

    private UserRequest createInvalidUserRequest() {
        UserRequest request = new UserRequest();
        // Missing required fields
        return request;
    }
} 