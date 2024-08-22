package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {

    @Schema(example = "string, osId for userId here")
    private String userId;

    private GenericDetails genericDetails;
    private UserPreference userPreference;
    private List<Skill> skills;
    private ConsentDetails consentDetails;
    private OnboardingDetails onboardDetails;
    private String referenceChannelId;
    private VolunteeringHoursRequest volunteeringHours;
}
