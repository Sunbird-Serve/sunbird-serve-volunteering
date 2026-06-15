package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.request.AgencyUpdateRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import com.sunbird.serve.volunteering.usermanagement.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserById(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserById(userId, headers);
    }

    @GetMapping("/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserByEmail(
            @RequestParam String email,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserByEmail(email, headers);
    }

    @GetMapping("/mobile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserByMobile(
            @RequestParam String mobile,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserByMobile(mobile, headers);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader Map<String, String> headers) {
        return userManagementService.getAllUsers(headers);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    public ResponseEntity<List<User>> getUserByStatus(
            @RequestParam String status,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserByStatus(status, headers);
    }

    @GetMapping("/agencyId")
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    public ResponseEntity<List<User>> getUserByAgencyId(
            @RequestParam String agencyId,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserByAgencyId(agencyId, headers);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    public ResponseEntity<List<User>> getUsers(@RequestHeader Map<String, String> headers) {
        return userManagementService.getUsers(headers);
    }

    @Operation(summary = "Create new user", description = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RcUserResponse> createUser(
            @Valid @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.createUser(userRequest, headers);
    }

    @Operation(summary = "Update user", description = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<RcUserResponse> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUser(userId, userRequest, headers);
    }

    @Operation(summary = "Create new user profile", description = "Create a user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user profile", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping(value = "/user-profile",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RcUserProfileResponse> createUserProfile(
            @Valid @RequestBody UserProfileRequest userProfileRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        logger.info("Received request to create user profile: {}", userProfileRequest);
        ResponseEntity<User> userResponseEntity = userManagementService.getUserById(userProfileRequest.getUserId(), headers);
        ResponseEntity<RcUserProfileResponse> responseEntity = userManagementService.createUserProfile(userProfileRequest, headers);
        if (responseEntity.getStatusCode().is2xxSuccessful()
                && userResponseEntity.getStatusCode().is2xxSuccessful()
                && userResponseEntity.getBody() != null) {
            String email = userResponseEntity.getBody().getContactDetails().getEmail();
            String volunteerName = userResponseEntity.getBody().getIdentityDetails().getFullname();
            userManagementService.sendEmail(email, volunteerName);
        }
        return responseEntity;
    }

    @Operation(summary = "Update user profile", description = "Update user profile details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated user profile details", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/user-profile/{userProfileId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<RcUserProfileResponse> updateUserProfile(
            @PathVariable String userProfileId,
            @Valid @RequestBody UserProfileRequest userProfileRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUserProfile(userProfileId, userProfileRequest, headers);
    }

    @GetMapping("/user-profile/{userProfileId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfile> getUserProfileById(
            @PathVariable String userProfileId,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserProfileById(userProfileId, headers);
    }

    @GetMapping("/user-profile/userId/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfile> getUserProfileByUserId(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers) {
        return userManagementService.getUserProfileByUserId(userId, headers);
    }

    @Operation(summary = "Update User Status", description = "Update User Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated user status", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/status/update/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    ResponseEntity<User> updateUserStatus(
            @PathVariable String userId,
            @RequestBody UserStatusRequest userStatusRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUserStatus(userId, userStatusRequest, headers);
    }

    @Operation(summary = "Update User Agency Details", description = "Update User Agency Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Agency Details", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/agencyId/update/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    ResponseEntity<User> updateUserAgency(
            @PathVariable String userId,
            @RequestBody AgencyUpdateRequest agencyUpdateRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUserAgency(userId, agencyUpdateRequest, headers);
    }
}
