package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteeringHoursRequest {
    private int totalHours;
    private int hoursPerWeek;
}
