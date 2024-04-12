package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    private Skills skills;
    private GenericDetails genericDetails;
    private UserPreference userPreference;
    private String agencyId;
    private String userId;
    private OnboardDetails onboardDetails;
    private ConsentDetails consentDetails;
    private String referenceChannelId;
    private VolunteeringHours volunteeringHours;
}
