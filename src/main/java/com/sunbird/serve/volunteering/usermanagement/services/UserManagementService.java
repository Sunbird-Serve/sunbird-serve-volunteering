package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;

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


@Service
public class UserManagementService {

    private final RcService rcService;

     @Autowired
    public UserManagementService(RcService rcService) {
        this.rcService = rcService;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<User> getUserById(String userId, Map<String, String> headers) {
        return ResponseEntity.ok(rcService.getUserById(userId));
    }

    public ResponseEntity<User> getUserByEmail(String email, Map<String, String> headers) {
        List<User> allUsers = rcService.getUsers();
        List<User> emailUsers = allUsers.stream()
                .filter(s -> s.getContactDetails().getEmail().equalsIgnoreCase(email)).toList();
        return ResponseEntity.ok(emailUsers.get(0));
    }


    public ResponseEntity<List<User>> getAllUsers(Map<String, String> headers) {
        List<User> allUsers = rcService.getUsers();
        return ResponseEntity.ok(allUsers);
    }

   
    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest, Map<String, String> headers) {
        return rcService.createUser(userRequest);
    }


    public ResponseEntity<RcUserResponse> updateUser(String userId, UserRequest userRequest, Map<String, String> headers) {
        return rcService.updateUser(userRequest, userId);
    }

    public ResponseEntity<RcUserProfileResponse> createUserProfile(UserProfileRequest userProfileRequest, Map<String, String> headers) {
        return rcService.createUserProfile(userProfileRequest);
    }

    public ResponseEntity<RcUserProfileResponse> updateUserProfile(String userProfileId, UserProfileRequest userProfileRequest, Map<String, String> headers) {
        return rcService.updateUserProfile(userProfileRequest, userProfileId);
    }

    public ResponseEntity<UserProfile> getUserProfileById(String userProfileId, Map<String, String> headers) {
        return ResponseEntity.ok(rcService.getUserProfileById(userProfileId));
    }

    public void sendEmail(String email, String volunteerName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
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

            javaMailSender.send(mimeMessage); //send email

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
