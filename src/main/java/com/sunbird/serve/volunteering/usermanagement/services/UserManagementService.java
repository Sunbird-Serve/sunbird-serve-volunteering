package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserProfile.UserProfile;
import com.sunbird.serve.volunteering.usermanagement.services.RcService;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.User;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;
import java.util.Map;
import java.util.List;


@Service
public class UserManagementService {

    private final RcService rcService;

     @Autowired
    public UserManagementService(RcService rcService) {
        this.rcService = rcService;
    }

    public ResponseEntity<User> getUserById(String userId, Map<String, String> headers) {
        return ResponseEntity.ok(rcService.getUserById(userId));
    }

    public ResponseEntity<User> getUserByEmail(String email, Map<String, String> headers) {
        List<User> allUsers = rcService.getUsers();
        List<User> emailUsers = allUsers.stream()
                .filter(s -> s.getContactDetails().getEmail().equalsIgnoreCase(email)).toList();
        return ResponseEntity.ok(emailUsers.get(0));
    }


    public ResponseEntity<List<User>> getAllUsers(Map<String, String> headers) {
        List<User> allUsers = rcService.getUsers();
        return ResponseEntity.ok(allUsers);
    }

   
    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest, Map<String, String> headers) {
        return rcService.createUser(userRequest);
    }


    public ResponseEntity<RcUserResponse> updateUser(String userId, UserRequest userRequest, Map<String, String> headers) {
        return rcService.updateUser(userRequest, userId);
    }

    public ResponseEntity<UserProfile> getUserProfile(String userId, Map<String, String> headers) {
        return rcService.getUserProfile(userId);
    }

}
