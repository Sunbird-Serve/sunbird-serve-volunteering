package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.CalculateVolHoursRequest;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.VolunteeringHours;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.VolunteeringHoursRequest;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import com.sunbird.serve.volunteering.usermanagement.services.UserManagementService;
import com.sunbird.serve.volunteering.usermanagement.services.VolunteerManagementService;
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
@RequestMapping("/volunteer")
public class VolunteerManagementController {

    private final VolunteerManagementService volunteerManagementService;


    @Autowired
    public VolunteerManagementController(VolunteerManagementService volunteerManagementService) {
        this.volunteerManagementService = volunteerManagementService;
    }


    @GetMapping("volunteer-hours/read/{userId}")
    public ResponseEntity<VolunteeringHours> getVolHrsByUserId(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers
    ) {
        return volunteerManagementService.getVolHrsByUserId(userId, headers);
    }


    @Operation(summary = "Update Volunteering Hours", description = "Update Volunteering Hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated volunteer hours", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PutMapping(value = "/volunteer-hours/update/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<VolunteeringHours> updateVolunteerHours(
            @PathVariable String userId,
            @RequestBody VolunteeringHoursRequest volHoursRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return volunteerManagementService.updateVolunteerHours(userId, volHoursRequest, headers);
    } 

 @Operation(summary = "Create Volunteering Hours", description = "Create Volunteering Hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created volunteer hours", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/volunteer-hours/create/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<VolunteeringHours> createVolunteerHours(
            @PathVariable String userId,
            @RequestBody CalculateVolHoursRequest calculateVolHoursRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return volunteerManagementService.calculateVolHours(userId, calculateVolHoursRequest, headers);
    } 
}
