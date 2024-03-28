package com.sunbird.serve.volunteering.models.request.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Definitions{
	private UserProfile userProfile;
	private GenericDetails genericDetails;
	private Skills skills;
	private UserPreference userPreference;
	private VolunteeringHours volunteeringHours;
	private TimePreferred timePreferred;
	private Language language;
	private InterestAreas interestArea;
	private DayPreferred dayPreferred;
	private OnboardStep onboardStep;
	private OnboardStatus onboardStatus;
	private Skill skill;
}
