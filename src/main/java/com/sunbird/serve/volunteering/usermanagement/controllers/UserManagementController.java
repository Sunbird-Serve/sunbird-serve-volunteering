package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.usermanagement.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serve-volunteering/users")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers
    ) {
        return userManagementService.getUserById(userId, headers);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(
            @RequestParam String email,
            @RequestHeader Map<String, String> headers
    ) {
        return userManagementService.getUserByEmail(email, headers);
    }

    @GetMapping("/read")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader Map<String, String> headers) {
        return userManagementService.getAllUsers(headers);
    }
}
