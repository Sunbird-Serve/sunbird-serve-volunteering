package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile{
	private String referenceChannelId;
	private String osUpdatedAt;
	private GenericDetails genericDetails;
	private String osUpdatedBy;
	private ConsentDetails consentDetails;
	private String osid;
	private String userId;
	private List<String> osOwner;
	private OnboardDetails onboardDetails;
	private List<SkillsItem> skills;
	private UserPreference userPreference;
	private String osCreatedAt;
	private String osCreatedBy;
	private VolunteeringHours volunteeringHours;
}