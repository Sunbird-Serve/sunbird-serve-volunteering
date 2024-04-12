package com.sunbird.serve.volunteering.models.response.RcUserProfileResponse;

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
	private List<String> timePreferred;
	private List<String> dayPreferred;
	private List<String> interestAreas;
	private List<String> language;
}