package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDetails{
	private String osUpdatedAt;
	private String osCreatedAt;
	private String osUpdatedBy;
	private String osCreatedBy;
	private String consentDescription;
	private boolean consentGiven;
	private String osid;
	private String consentDate;
}
