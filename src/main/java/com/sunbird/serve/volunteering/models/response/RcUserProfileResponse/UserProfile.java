package com.sunbird.serve.volunteering.models.response.RcUserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile{
	private String referenceChannelId;
	private GenericDetails genericDetails;
	private UserPreference userPreference;
	private String userId;
	private String agencyId;
	private Skills skills;
	private ConsentDetails consentDetails;
	private OnboardDetails onboardDetails;
	private VolunteeringHours volunteeringHours;
}
