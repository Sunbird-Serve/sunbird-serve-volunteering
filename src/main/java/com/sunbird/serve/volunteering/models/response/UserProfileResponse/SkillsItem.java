package com.sunbird.serve.volunteering.models.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillsItem{
	private String skillName;
	private String osUpdatedAt;
	private String osCreatedAt;
	private String osUpdatedBy;
	private String osCreatedBy;
	private String osid;
	private String skillLevel;
}
