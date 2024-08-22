package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardStatusItem{
	private String osUpdatedAt;
	private String osCreatedAt;
	private String osUpdatedBy;
	private String osCreatedBy;
	private String onboardStep;
	private String osid;
	private String status;
}
