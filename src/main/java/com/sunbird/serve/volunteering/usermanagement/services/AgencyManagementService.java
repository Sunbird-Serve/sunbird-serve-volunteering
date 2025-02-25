package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;

import com.sunbird.serve.volunteering.models.request.AgencyRequest;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.response.Agency;
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



@Service
public class AgencyManagementService {

    private final RcService rcService;

     @Autowired
    public AgencyManagementService(RcService rcService) {
        this.rcService = rcService;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<Agency> getAgencyById(String agencyId, Map<String, String> headers) {
        return ResponseEntity.ok(rcService.getAgencyById(agencyId));
    }
 
    public ResponseEntity<RcUserResponse> createAgency(AgencyRequest agencyRequest, Map<String, String> headers) {
        return rcService.createAgency(agencyRequest);
    }

}
