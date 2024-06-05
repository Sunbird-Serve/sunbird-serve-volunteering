package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import com.sunbird.serve.volunteering.usermanagement.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
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

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader Map<String, String> headers) {
        return userManagementService.getAllUsers(headers);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getUsers(@RequestHeader Map<String, String> headers) {
        return userManagementService.getUsers(headers);
    }

    @Operation(summary = "Create new user", description = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<RcUserResponse> createUser(
            @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.createUser(userRequest, headers);
    }


    @Operation(summary = "Update user", description = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PutMapping(value = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<RcUserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUser(userId, userRequest, headers);
    }

    @Operation(summary = "Create new user profile", description = "Create a user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user profile", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/user-profile",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<RcUserProfileResponse> createUserProfile(
            @RequestBody UserProfileRequest userProfileRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        ResponseEntity<User> userResponseEntity = userManagementService.getUserById(userProfileRequest.getUserId(), headers);
        ResponseEntity<RcUserProfileResponse> responseEntity = userManagementService.createUserProfile(userProfileRequest, headers);
        if (responseEntity.getStatusCode().is2xxSuccessful()
                && userResponseEntity.getStatusCode().is2xxSuccessful()
                && userResponseEntity.getBody() != null
        ) {
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
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PutMapping(value = "/user-profile/{userProfileId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<RcUserProfileResponse> updateUserProfile(
            @PathVariable String userProfileId,
            @RequestBody UserProfileRequest userProfileRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUserProfile(userProfileId, userProfileRequest, headers);
    }
    @GetMapping("/user-profile/{userProfileId}")
    public ResponseEntity<UserProfile> getUserProfileById(
            @PathVariable String userProfileId,
            @RequestHeader Map<String, String> headers
    ) {
        return userManagementService.getUserProfileById(userProfileId, headers);
    }

    @GetMapping("/user-profile/userId/{userId}")
    public ResponseEntity<UserProfile> getUserProfileByUserId(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers
    ) {
        return userManagementService.getUserProfileByUserId(userId, headers);
    }

    @Operation(summary = "Update User Status", description = "Update User Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated user status", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PutMapping(value = "/status/update/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<User> updateUserStatus(
            @PathVariable String userId,
            @RequestBody UserStatusRequest userStatusRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return userManagementService.updateUserStatus(userId, userStatusRequest, headers);
    } 

}
