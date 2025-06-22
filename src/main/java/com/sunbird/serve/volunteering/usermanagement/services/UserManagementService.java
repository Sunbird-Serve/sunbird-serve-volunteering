package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;

import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.request.AgencyUpdateRequest;
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
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;



@Service
@Slf4j
public class UserManagementService {

    private final RcService rcService;

     @Autowired
    public UserManagementService(RcService rcService) {
        this.rcService = rcService;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<User> getUserById(String userId, Map<String, String> headers) {
        try {
            log.info("Fetching user by ID: {}", userId);
            User user = rcService.getUserById(userId);
            if (user == null) {
                log.warn("User not found with ID: {}", userId);
                return ResponseEntity.notFound().build();
            }
            log.info("Successfully fetched user: {}", userId);
            return ResponseEntity.ok(user);
        } catch (WebClientResponseException e) {
            log.error("Error fetching user by ID {}: {}", userId, e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error fetching user by ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<User> getUserByEmail(String email, Map<String, String> headers) {
        try {
            log.info("Fetching user by email: {}", email);
            List<User> allUsers = rcService.getAllUsers();
            List<User> emailUsers = allUsers.stream()
                    .filter(s -> s.getContactDetails() != null && 
                               s.getContactDetails().getEmail() != null &&
                               s.getContactDetails().getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
            
            if (emailUsers.isEmpty()) {
                log.warn("No user found with email: {}", email);
                return ResponseEntity.notFound().build();
            }
            
            log.info("Successfully fetched user by email: {}", email);
            return ResponseEntity.ok(emailUsers.get(0));
        } catch (Exception e) {
            log.error("Error fetching user by email {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<User>> getUserByStatus(String status, Map<String, String> headers) {
        try {
            log.info("Fetching users by status: {}", status);
            List<User> allUsers = rcService.getAllUsers();
            List<User> statusUsers = allUsers.stream()
                    .filter(s -> s.getStatus() != null && s.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
            
            log.info("Found {} users with status: {}", statusUsers.size(), status);
            return ResponseEntity.ok(statusUsers);
        } catch (Exception e) {
            log.error("Error fetching users by status {}: {}", status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<User>> getUserByAgencyId(String agencyId, Map<String, String> headers) {
        try {
            log.info("Fetching users by agency ID: {}", agencyId);
            List<User> allUsers = rcService.getAllUsers();
            List<User> agencyUsers = allUsers.stream()
                    .filter(s -> s.getAgencyId() != null && s.getAgencyId().equalsIgnoreCase(agencyId))
                    .collect(Collectors.toList());
            
            log.info("Found {} users for agency: {}", agencyUsers.size(), agencyId);
            return ResponseEntity.ok(agencyUsers);
        } catch (Exception e) {
            log.error("Error fetching users by agency ID {}: {}", agencyId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<List<User>> getAllUsers(Map<String, String> headers) {
        try {
            log.info("Fetching all users");
            List<User> allUsers = rcService.getUsers();
            log.info("Successfully fetched {} users", allUsers.size());
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            log.error("Error fetching all users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<User>> getUsers(Map<String, String> headers) {
        try {
            log.info("Fetching all users (alternative method)");
            List<User> allUsers = rcService.getAllUsers();
            log.info("Successfully fetched {} users", allUsers.size());
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            log.error("Error fetching all users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   
    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest, Map<String, String> headers) {
        try {
            log.info("Creating new user with email: {}", 
                userRequest.getContactDetails() != null ? userRequest.getContactDetails().getEmail() : "N/A");
            ResponseEntity<RcUserResponse> response = rcService.createUser(userRequest);
            log.info("Successfully created user with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<RcUserResponse> updateUser(String userId, UserRequest userRequest, Map<String, String> headers) {
        try {
            log.info("Updating user with ID: {}", userId);
            ResponseEntity<RcUserResponse> response = rcService.updateUser(userRequest, userId);
            log.info("Successfully updated user with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error updating user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error updating user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<RcUserProfileResponse> createUserProfile(UserProfileRequest userProfileRequest, Map<String, String> headers) {
        try {
            log.info("Creating user profile for user ID: {}", userProfileRequest.getUserId());
            ResponseEntity<RcUserProfileResponse> response = rcService.createUserProfile(userProfileRequest);
            log.info("Successfully created user profile with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error creating user profile: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error creating user profile: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<RcUserProfileResponse> updateUserProfile(String userProfileId, UserProfileRequest userProfileRequest, Map<String, String> headers) {
        try {
            log.info("Updating user profile with ID: {}", userProfileId);
            ResponseEntity<RcUserProfileResponse> response = rcService.updateUserProfile(userProfileRequest, userProfileId);
            log.info("Successfully updated user profile with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error updating user profile {}: {}", userProfileId, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error updating user profile {}: {}", userProfileId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<UserProfile> getUserProfileById(String userProfileId, Map<String, String> headers) {
        try {
            log.info("Fetching user profile by ID: {}", userProfileId);
            UserProfile userProfile = rcService.getUserProfileById(userProfileId);
            if (userProfile == null) {
                log.warn("User profile not found with ID: {}", userProfileId);
                return ResponseEntity.notFound().build();
            }
            log.info("Successfully fetched user profile: {}", userProfileId);
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            log.error("Error fetching user profile by ID {}: {}", userProfileId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<UserProfile> getUserProfileByUserId(String userId, Map<String, String> headers) {
        try {
            log.info("Fetching user profile by user ID: {}", userId);
            List<UserProfile> allUserProfiles = rcService.getUserProfiles(userId);
            List<UserProfile> filteredProfiles = allUserProfiles.stream()
                .filter(profile -> profile.getUserId() != null && profile.getUserId().equals(userId))
                .collect(Collectors.toList());
        
            if (filteredProfiles.isEmpty()) {
                log.warn("No user profile found for user ID: {}", userId);
                return ResponseEntity.notFound().build();
            }
            
            UserProfile userProfile = filteredProfiles.get(0);
            log.info("Successfully fetched user profile for user: {}", userId);
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            log.error("Error fetching user profile by user ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public void sendEmail(String email, String volunteerName) {
        try {
            log.info("Sending welcome email to: {} for volunteer: {}", email, volunteerName);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Welcome Aboard SERVE's Volunteer Squad!");
            mimeMessageHelper.setText("Dear " + volunteerName +",<br>"+
                    "<br>" +
                    "Thank you for registering as a volunteer with us! We are thrilled to have you join us and contribute to our mission of providing quality education for children of our country leveraging technology." +
                    "<br>" +
                    "Your dedication and support are invaluable to us, and we're excited to work together to make a positive impact in our community." +
                    "<br>" +
                    "As a registered volunteer, you now can nominate a need by browsing through a catalog of needs https://serve-v1.evean.net/vneedtypes. Please take some time to familiarize yourself with these needs to make the most out of your volunteering experience." +
                    "<br>" +
                    "We'll be in touch soon with details about upcoming volunteer opportunities and events. In the meantime, if you have any questions or need assistance, feel free to reach out to us at volunteer@evean.net\n" +
                    "<br><br>" +
                    "Regards, <br>" +
                    "Admin",true);

            javaMailSender.send(mimeMessage);
            log.info("Successfully sent welcome email to: {}", email);

        } catch (MessagingException e) {
            log.error("Error sending email to {}: {}", email, e.getMessage(), e);
            // Don't throw exception as email failure shouldn't break the main flow
        } catch (Exception e) {
            log.error("Unexpected error sending email to {}: {}", email, e.getMessage(), e);
        }
    }

    public ResponseEntity<User> updateUserStatus(String userId, UserStatusRequest userStatusRequest, Map<String, String> headers) {
        try {
            log.info("Updating user status for user ID: {} to status: {}", userId, userStatusRequest.getStatus());
            ResponseEntity<User> response = rcService.updateUserStatus(userId, userStatusRequest);
            log.info("Successfully updated user status with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error updating user status for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error updating user status for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<User> updateUserAgency(String userId, AgencyUpdateRequest agencyUpdateRequest, Map<String, String> headers) {
        try {
            log.info("Updating user agency for user ID: {} to agency: {}", userId, agencyUpdateRequest.getAgencyId());
            ResponseEntity<User> response = rcService.updateUserAgency(userId, agencyUpdateRequest);
            log.info("Successfully updated user agency with status: {}", response.getStatusCode());
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error updating user agency for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Unexpected error updating user agency for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
