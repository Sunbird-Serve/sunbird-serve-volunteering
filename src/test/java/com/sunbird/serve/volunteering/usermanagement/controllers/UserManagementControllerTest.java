package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.ContactDetails;
import com.sunbird.serve.volunteering.models.request.IdentityDetails;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.Params;
import com.sunbird.serve.volunteering.models.response.UserContactDetails;
import com.sunbird.serve.volunteering.usermanagement.services.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest {

    @Mock
    private UserManagementService userManagementService;

    @InjectMocks
    private UserManagementController userManagementController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userManagementController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        // Given
        String userId = "test-user-id";
        User user = createTestUser();
        when(userManagementService.getUserById(userId, any())).thenReturn(ResponseEntity.ok(user));

        // When & Then
        mockMvc.perform(get("/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.osid").value(user.getOsid()))
                .andExpect(jsonPath("$.status").value(user.getStatus()));
    }

    @Test
    void createUser_WithValidRequest_ShouldReturnSuccess() throws Exception {
        // Given
        UserRequest userRequest = createValidUserRequest();
        RcUserResponse response = new RcUserResponse();
        response.setParams(new Params());
        response.getParams().setStatus("SUCCESS");
        
        when(userManagementService.createUser(any(UserRequest.class), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        // When & Then
        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.params.status").value("SUCCESS"));
    }

    @Test
    void createUser_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Given
        UserRequest invalidRequest = createInvalidUserRequest();

        // When & Then
        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllUsers_ShouldReturnUserList() throws Exception {
        // Given
        List<User> users = Arrays.asList(createTestUser(), createTestUser());
        when(userManagementService.getAllUsers(any())).thenReturn(ResponseEntity.ok(users));

        // When & Then
        mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getUserByStatus_ShouldReturnFilteredUsers() throws Exception {
        // Given
        String status = "Active";
        List<User> users = Arrays.asList(createTestUser());
        when(userManagementService.getUserByStatus(status, any())).thenReturn(ResponseEntity.ok(users));

        // When & Then
        mockMvc.perform(get("/user/status")
                .param("status", status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    private User createTestUser() {
        User user = new User();
        user.setOsid("test-osid");
        user.setStatus("Active");
        user.setRole(Arrays.asList("Volunteer"));
        user.setAgencyId("test-agency");
        
        UserContactDetails contactDetails = new UserContactDetails();
        contactDetails.setEmail("test@example.com");
        contactDetails.setMobile("1234567890");
        user.setContactDetails(contactDetails);
        
        com.sunbird.serve.volunteering.models.response.IdentityDetails identityDetails = new com.sunbird.serve.volunteering.models.response.IdentityDetails();
        identityDetails.setFullname("Test User");
        identityDetails.setGender("Male");
        user.setIdentityDetails(identityDetails);
        
        return user;
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