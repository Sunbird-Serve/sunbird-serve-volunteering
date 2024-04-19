package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteeringHours{
	private String osUpdatedAt;
	private int hoursPerWeek;
	private String osCreatedAt;
	private int totalHours;
	private String osUpdatedBy;
	private String osCreatedBy;
	private String osid;
}
