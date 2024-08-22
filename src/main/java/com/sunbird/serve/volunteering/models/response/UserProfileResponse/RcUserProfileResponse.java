package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import com.sunbird.serve.volunteering.models.response.UserResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RcUserProfileResponse{
	private String ver;
	private long ets;
	private String id;
	private Params params;
	private String responseCode;

}
