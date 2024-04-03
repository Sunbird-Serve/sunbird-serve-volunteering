package com.sunbird.serve.volunteering.models.response.RcUserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RcUserProfileResponse{
	private UserProfile userProfile;
}
