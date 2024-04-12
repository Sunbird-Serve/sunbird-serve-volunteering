package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericDetails{
	private String qualification;
	private String affiliation;
	private String yearsOfExperience;
	private String employmentStatus;
}
