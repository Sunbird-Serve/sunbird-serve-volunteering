package com.sunbird.serve.volunteering.models.request.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skills{
	private ConsentDetails consentDetails;
	private String description;
	private String type;
	private String title;
	private Definitions definitions;
	private OnboardingDetails onboardingDetails;
	private Properties properties;
	private String id;
	private Items items;
	private String ref;
}
