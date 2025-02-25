package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.response.Agency;
import com.sunbird.serve.volunteering.models.request.AgencyRequest;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import com.sunbird.serve.volunteering.usermanagement.services.AgencyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/agency")
public class AgencyManagementController {

    private final AgencyManagementService agencyManagementService;
    private static final Logger logger = LoggerFactory.getLogger(AgencyManagementController.class);


    @Autowired
    public AgencyManagementController(AgencyManagementService agencyManagementService) {
        this.agencyManagementService = agencyManagementService;
    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<Agency> getAgencyById(
            @PathVariable String agencyId,
            @RequestHeader Map<String, String> headers
    ) {
        return agencyManagementService.getAgencyById(agencyId, headers);
    }


   @Operation(summary = "Create new agency", description = "Create an agency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created an agency", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<RcUserResponse> createAgency(
            @RequestBody AgencyRequest agencyRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return agencyManagementService.createAgency(agencyRequest, headers);
    }

}
