package com.sunbird.serve.volunteering.models.request.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Properties{
	private UserProfile userProfile;
	private HoursPerWeek hoursPerWeek;
	private TotalHours totalHours;
	private TimePreferred timePreferred;
	private DayPreferred dayPreferred;
	private InterestAreas interestAreas;
	private Language language;
	private Skills skills;
	private ProfileCompletion profileCompletion;
	private OnboardStatus onboardStatus;
	private RefreshPeriod refreshPeriod;
	private OnboardStep onboardStep;
	private Status status;
	private SkillName skillName;
	private SkillLevel skillLevel;
	private ConsentDescription consentDescription;
	private ConsentGiven consentGiven;
	private ConsentDate consentDate;
	private Qualification qualification;
	private Affiliation affiliation;
	private YearsOfExperience yearsOfExperience;
	private EmploymentStatus employmentStatus;
	private ReferenceChannelId referenceChannelId;
	private GenericDetails genericDetails;
	private UserPreference userPreference;
	private ConsentDetails consentDetails;
	private OnboardDetails onboardDetails;
	private VolunteeringHours volunteeringHours;
}
