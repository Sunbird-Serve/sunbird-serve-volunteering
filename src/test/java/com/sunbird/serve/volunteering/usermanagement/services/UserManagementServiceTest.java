package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.ContactDetails;
import com.sunbird.serve.volunteering.models.request.IdentityDetails;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.UserContactDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private RcService rcService;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private UserManagementService userManagementService;

    private Map<String, String> headers;

    @BeforeEach
    void setUp() {
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer test-token");
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Given
        String userId = "test-user-id";
        User expectedUser = createTestUser();
        when(rcService.getUserById(userId)).thenReturn(expectedUser);

        // When
        ResponseEntity<User> response = userManagementService.getUserById(userId, headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        verify(rcService).getUserById(userId);
    }

    @Test
    void getUserByEmail_ShouldReturnUser() {
        // Given
        String email = "test@example.com";
        List<User> allUsers = Arrays.asList(createTestUser(), createTestUserWithDifferentEmail());
        when(rcService.getAllUsers()).thenReturn(allUsers);

        // When
        ResponseEntity<User> response = userManagementService.getUserByEmail(email, headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(email, response.getBody().getContactDetails().getEmail());
        verify(rcService).getAllUsers();
    }

    @Test
    void getUserByStatus_ShouldReturnFilteredUsers() {
        // Given
        String status = "Active";
        List<User> allUsers = Arrays.asList(createTestUser(), createInactiveUser());
        when(rcService.getAllUsers()).thenReturn(allUsers);

        // When
        ResponseEntity<List<User>> response = userManagementService.getUserByStatus(status, headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(status, response.getBody().get(0).getStatus());
        verify(rcService).getAllUsers();
    }

    @Test
    void createUser_ShouldReturnSuccessResponse() {
        // Given
        UserRequest userRequest = createValidUserRequest();
        RcUserResponse expectedResponse = new RcUserResponse();
        when(rcService.createUser(userRequest)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(expectedResponse));

        // When
        ResponseEntity<RcUserResponse> response = userManagementService.createUser(userRequest, headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(rcService).createUser(userRequest);
    }

    @Test
    void updateUser_ShouldReturnSuccessResponse() {
        // Given
        String userId = "test-user-id";
        UserRequest userRequest = createValidUserRequest();
        RcUserResponse expectedResponse = new RcUserResponse();
        when(rcService.updateUser(userRequest, userId)).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<RcUserResponse> response = userManagementService.updateUser(userId, userRequest, headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(rcService).updateUser(userRequest, userId);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Given
        List<User> expectedUsers = Arrays.asList(createTestUser(), createTestUser());
        when(rcService.getUsers()).thenReturn(expectedUsers);

        // When
        ResponseEntity<List<User>> response = userManagementService.getAllUsers(headers);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
        verify(rcService).getUsers();
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

    private User createTestUserWithDifferentEmail() {
        User user = createTestUser();
        ((UserContactDetails)user.getContactDetails()).setEmail("different@example.com");
        return user;
    }

    private User createInactiveUser() {
        User user = createTestUser();
        user.setStatus("Inactive");
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
} 