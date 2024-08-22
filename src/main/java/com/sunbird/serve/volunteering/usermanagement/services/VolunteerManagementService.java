package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.CalculateVolHoursRequest;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.VolunteeringHoursRequest;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.VolunteeringHours;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class VolunteerManagementService {

    private final RcService rcService;
    private static final Logger logger = LoggerFactory.getLogger(VolunteerManagementService.class);

     @Autowired
    public VolunteerManagementService(RcService rcService) {
        this.rcService = rcService;
    }


    public ResponseEntity<VolunteeringHours> getVolHrsByUserId(String userId, Map<String, String> headers) {
        List<UserProfile> allUserProfiles = rcService.getUserProfiles(userId);

        List<UserProfile> filteredProfiles = allUserProfiles.stream()
            .filter(profile -> profile.getUserId().equals(userId))
            .collect(Collectors.toList());

         if (filteredProfiles.isEmpty()) {
        // Handle the case when no user profile is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

        UserProfile userProfile = filteredProfiles.get(0);

        logger.info("User Profile Vol Hrs: {}", userProfile);

        return ResponseEntity.ok(userProfile.getVolunteeringHours());
    }

    public ResponseEntity<VolunteeringHours> updateVolunteerHours(String userId, VolunteeringHoursRequest volHoursRequest, Map<String, String> headers) {
        List<UserProfile> allUserProfiles = rcService.getUserProfiles(userId);

        List<UserProfile> filteredProfiles = allUserProfiles.stream()
            .filter(profile -> profile.getUserId().equals(userId))
            .collect(Collectors.toList());

         if (filteredProfiles.isEmpty()) {
        // Handle the case when no user profile is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

        UserProfile userProfile = filteredProfiles.get(0);
        
        return rcService.updateVolunteerHours(volHoursRequest, userProfile.getOsid());
    }


    public ResponseEntity<VolunteeringHours> calculateVolHours(String userId, CalculateVolHoursRequest calculateVolHoursRequest, Map<String, String> headers) {
        List<UserProfile> allUserProfiles = rcService.getUserProfiles(userId);

        List<UserProfile> filteredProfiles = allUserProfiles.stream()
            .filter(profile -> profile.getUserId().equals(userId))
            .collect(Collectors.toList());

         if (filteredProfiles.isEmpty()) {
        // Handle the case when no user profile is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserProfile userProfile = filteredProfiles.get(0);

        ResponseEntity<VolunteeringHours> responseEntity = getVolHrsByUserId(userId, headers);

        VolunteeringHours volunteeringHours = responseEntity.getBody();

        VolunteeringHoursRequest volHoursRequest = new VolunteeringHoursRequest();

        volHoursRequest.setTotalHours(volunteeringHours.getTotalHours());
        
        return rcService.updateVolunteerHours(volHoursRequest, userProfile.getOsid());
    }
}
