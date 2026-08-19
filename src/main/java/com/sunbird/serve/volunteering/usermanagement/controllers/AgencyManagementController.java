package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.response.Agency;
import com.sunbird.serve.volunteering.models.request.AgencyRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.usermanagement.services.AgencyManagementService;
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
@RequestMapping("/agency")
public class AgencyManagementController {

    private final AgencyManagementService agencyManagementService;
    private static final Logger logger = LoggerFactory.getLogger(AgencyManagementController.class);

    @Autowired
    public AgencyManagementController(AgencyManagementService agencyManagementService) {
        this.agencyManagementService = agencyManagementService;
    }

    @GetMapping("/{agencyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Agency> getAgencyById(
            @PathVariable String agencyId,
            @RequestHeader Map<String, String> headers) {
        return agencyManagementService.getAgencyById(agencyId, headers);
    }

    @Operation(summary = "Create new agency", description = "Create an agency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created an agency", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'vAdmin')")
    public ResponseEntity<RcUserResponse> createAgency(
            @Valid @RequestBody AgencyRequest agencyRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return agencyManagementService.createAgency(agencyRequest, headers);
    }

    @Operation(summary = "Update an agency", description = "Update an existing agency by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the agency", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "404", description = "Agency not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @PutMapping(value = "/{agencyId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'vAdmin')")
    public ResponseEntity<Agency> updateAgency(
            @PathVariable String agencyId,
            @Valid @RequestBody AgencyRequest agencyRequest,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return agencyManagementService.updateAgency(agencyId, agencyRequest, headers);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('sAdmin', 'nAdmin', 'nCoordinator', 'vAdmin', 'vCoordinator')")
    public ResponseEntity<List<Agency>> getAllAgency(@RequestHeader Map<String, String> headers) {
        return agencyManagementService.getAllAgency(headers);
    }
}
