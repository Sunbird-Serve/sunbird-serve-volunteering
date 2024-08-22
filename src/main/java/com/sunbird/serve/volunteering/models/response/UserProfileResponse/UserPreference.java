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
public class UserPreference{
	private String osUpdatedAt;
	private List<String> timePreferred;
	private List<String> interestArea;
	private String osCreatedAt;
	private String osUpdatedBy;
	private List<String> dayPreferred;
	private String osCreatedBy;
	private List<String> language;
	private String osid;
}