package com.sunbird.serve.volunteering.usermanagement.services;

import com.sunbird.serve.volunteering.models.request.Status;
import com.sunbird.serve.volunteering.models.request.StatusFilter;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.UserProfileRequest;
import com.sunbird.serve.volunteering.models.request.UserRequest;
import com.sunbird.serve.volunteering.models.request.AgencyRequest;
import com.sunbird.serve.volunteering.models.request.UserStatusRequest;
import com.sunbird.serve.volunteering.models.request.AgencyUpdateRequest;
import com.sunbird.serve.volunteering.models.request.UserProfileRequest.VolunteeringHoursRequest;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.VolunteeringHours;
import com.sunbird.serve.volunteering.models.request.UsersSearchPage;
import com.sunbird.serve.volunteering.models.request.UserProfileSearch;
import com.sunbird.serve.volunteering.models.request.Filters;
import com.sunbird.serve.volunteering.models.request.UserIdFilter;
import com.sunbird.serve.volunteering.models.response.RcUserResponse;
import com.sunbird.serve.volunteering.models.response.User;
import com.sunbird.serve.volunteering.models.response.Agency;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.RcUserProfileResponse;
import com.sunbird.serve.volunteering.models.response.UserProfileResponse.UserProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class RcService {

    @Autowired
    WebClient rcClient;

    public User getUserById(String userId) {
        return rcClient.get()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

     public Agency getAgencyById(String agencyId) {
        return rcClient.get()
                .uri((uriBuilder -> uriBuilder
                        .path("/Agency/{id}")
                        .build(agencyId)
                ))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Agency.class)
                .block();
    }

    public List<User> getUsers() {
        ResponseEntity<List<User>> responseEntity = listUsers("Active");
        return responseEntity.getBody();
    }

    public ResponseEntity<List<User>> listUsers (String status) {
        return rcClient.post()
                .uri("/Users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UsersSearchPage.builder()
                        .offset(0)
                        .limit(500)
                        .filters(StatusFilter.builder()
                                .status(Status.builder()
                                        .eq(status)
                                        .build())
                                .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(User.class)
                .block();
    }

    /*public ResponseEntity<List<User>> getAllusers(List<User> allUsers) {
        return rcClient.post()
                .uri("/Users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UsersSearchPage.builder()
                        .offset(0)
                        .limit(10)
                        .filters(StatusFilter.builder()
                                .status(Status.builder()
                                        .build())
                                .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(User.class)
                .block();

    }*/

    public List<User> getAllUsers() {
        return rcClient.get()
                .uri("/Users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();

    }

    public List<Agency> getAllAgency() {
        return rcClient.get()
                .uri("/Agency")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Agency.class)
                .collectList()
                .block();

    }

    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest) {
        return rcClient.post()
                .uri("/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserResponse.class)
                .block();
    }

    public ResponseEntity<RcUserResponse> createAgency(AgencyRequest agencyRequest) {
        return rcClient.post()
                .uri("/Agency")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(agencyRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserResponse.class)
                .block();
    }

    public ResponseEntity<RcUserResponse> updateUser(UserRequest userRequest, String userId) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserResponse.class)
                .block();
    }


    public ResponseEntity<RcUserProfileResponse> createUserProfile(UserProfileRequest userProfileRequest) {
        return rcClient.post()
                .uri("/UserProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userProfileRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserProfileResponse.class)
                .block();
    }

    public UserProfile getUserProfileById(String userProfileId) {
        return rcClient.get()
                .uri((uriBuilder -> uriBuilder
                        .path("/UserProfile/{id}")
                        .build(userProfileId)
                ))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserProfile.class)
                .block();
    }

        public List<UserProfile> getUserProfiles(String userId) {
                ResponseEntity<List<UserProfile>> responseEntity = listUserProfiles(userId);
                return responseEntity.getBody();
    }

    public ResponseEntity<List<UserProfile>> listUserProfiles (String userId) {
        return rcClient.post()
                .uri("/UserProfile/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UserProfileSearch.builder()
                        .offset(0)
                        .limit(10)
                        .filters(Filters.builder()
                                .userId(UserIdFilter.builder()
                                .eq(userId)
                                .build())
                            .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(UserProfile.class)
                .block();
    }

public ResponseEntity<RcUserProfileResponse> updateUserProfile(UserProfileRequest userProfileRequest, String userProfileId) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/UserProfile/{id}")
                        .build(userProfileId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userProfileRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserProfileResponse.class)
                .block();
    }

public ResponseEntity<VolunteeringHours> updateVolunteerHours(VolunteeringHoursRequest volHoursRequest, String userProfileId) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/UserProfile/{id}/volunteeringHours")
                        .build(userProfileId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(volHoursRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(VolunteeringHours.class)
                .block();
    }

    public ResponseEntity<User> updateUserStatus(String userId, UserStatusRequest userStatusRequest) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userStatusRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(User.class)
                .block();
    }

    public ResponseEntity<User> updateUserAgency(String userId, AgencyUpdateRequest agencyUpdateRequest) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(agencyUpdateRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(User.class)
                .block();
    }

}
