package com.sunbird.serve.volunteering.usermanagement.controllers;

import com.sunbird.serve.volunteering.models.VolunteerHours.UpdateVolunteeringHoursRequest;
import com.sunbird.serve.volunteering.models.VolunteerHours.VolunteeringHours;
import com.sunbird.serve.volunteering.usermanagement.services.VolunteerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class VolunteerManagementController {

    private final VolunteerManagementService volunteerManagementService;


    @Autowired
    public VolunteerManagementController(VolunteerManagementService volunteerManagementService) {
        this.volunteerManagementService = volunteerManagementService;
    }

    @GetMapping("/volunteer-hours/{userId}")
    public ResponseEntity<String> getVolunteerHoursByUserId(
            @PathVariable String userId,
            @RequestHeader Map<String, String> headers
    ) {
        Double totalHours = volunteerManagementService.getVolunteerHoursByUserId(userId, headers);
        if (totalHours == 0) {
            return ResponseEntity.ok("No volunteer hours recorded for user " + userId);
        } else {
            return ResponseEntity.ok("Total volunteer hours for " + userId + " is " + totalHours);
        }
    }


    @Operation(summary = "Update Volunteer Hours", description = "Update Volunteer Hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated volunteer hours", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PutMapping(value = "/volunteer-hours/{userId}/needDeliverableId/{needDeliverableId}")
    public ResponseEntity<?> updateVolunteerHours(
            @PathVariable String userId,
            @PathVariable String needDeliverableId,
            @RequestBody UpdateVolunteeringHoursRequest updateVolunteeringHoursRequest,
            @RequestHeader Map<String, String> headers) {

        if (updateVolunteeringHoursRequest.getDeliveryHours() < 0.0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delivery hours cannot be negative.");
        }

        VolunteeringHours updatedVolunteeringHours = volunteerManagementService.updateVolunteerHours(
                userId,
                needDeliverableId,
                updateVolunteeringHoursRequest.getDeliveryHours(),
                updateVolunteeringHoursRequest.getDeliveryDate(),
                headers
        );

        if (updatedVolunteeringHours != null) {
            return ResponseEntity.ok(updatedVolunteeringHours);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Volunteer hours not found for userId: " + userId + " and needDeliverableId: " + needDeliverableId);
        }
    }

    @Operation(summary = "Create Volunteer Hours", description = "Create Volunteer Hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created volunteer hours", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/volunteer-hours/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<VolunteeringHours> createVolunteerHours(
            @RequestBody VolunteeringHours volunteeringHours,
            @Parameter() @RequestHeader Map<String, String> headers) {
        return volunteerManagementService.createVolunteerHours(volunteeringHours, headers);
    }
}
