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
public class OnboardDetails{
	private String profileCompletion;
	private List<OnboardStatusItem> onboardStatus;
	private String refreshPeriod;
}