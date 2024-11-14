package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.VolunteerHours.VolunteeringHours;
import com.sunbird.serve.volunteering.usermanagement.repositories.VolunteerManagementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class VolunteerManagementService {

    private final VolunteerManagementRepository volunteerManagementRepository;

    private static final Logger logger = LoggerFactory.getLogger(VolunteerManagementService.class);

    @Autowired
    public VolunteerManagementService(VolunteerManagementRepository volunteerManagementRepository) {
        this.volunteerManagementRepository = volunteerManagementRepository;
    }


    public Double getVolunteerHoursByUserId(String userId, Map<String, String> headers) {
        try {
            List<VolunteeringHours> volunteeringHoursList = volunteerManagementRepository.findAllByUserId(userId);
            logger.info("Fetched volunteer hours list for userId {}: {}", userId, volunteeringHoursList);

            if (volunteeringHoursList.isEmpty()) {
                return (double) 0; // Return 0 if no hours found
            }

            return (volunteeringHoursList.stream()
                    .mapToDouble(VolunteeringHours::getDeliveryHours)
                    .sum());

        } catch (Exception e) {
            logger.error("Error retrieving volunteer hours for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error retrieving volunteer hours", e);
        }
    }


    public VolunteeringHours updateVolunteerHours(String userId, String needDeliverableId, Double deliveryHours, Instant deliveryDate, Map<String, String> headers) {
        try {
            Optional<VolunteeringHours> optionalVolunteerHours = volunteerManagementRepository.findByUserIdAndNeedDeliverableId(userId, needDeliverableId);

            if (optionalVolunteerHours.isPresent()) {
                VolunteeringHours volunteeringHours = optionalVolunteerHours.get();
                volunteeringHours.setDeliveryHours(deliveryHours);
                volunteeringHours.setDeliveryDate(deliveryDate);

                VolunteeringHours updatedVolunteeringHours = volunteerManagementRepository.save(volunteeringHours);
                logger.info("Updated VolunteeringHours for userId {}: {}", userId, updatedVolunteeringHours);
                return updatedVolunteeringHours;
            } else {
                logger.warn("VolunteeringHours not found for userId {} and needDeliverableId {}", userId, needDeliverableId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error updating volunteer hours for userId {} and needDeliverableId {}: {}", userId, needDeliverableId, e.getMessage());
            return null;
        }
    }


    public ResponseEntity<VolunteeringHours> createVolunteerHours(VolunteeringHours volunteeringHours, Map<String, String> headers) {
        try {
            VolunteeringHours saveVolunteeringHours = volunteerManagementRepository.save(volunteeringHours);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveVolunteeringHours);
        } catch (Exception e) {
            logger.error("Error creating volunteer hours: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

