package com.sunbird.serve.volunteering.models.VolunteerHours;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVolunteeringHoursRequest {

    private Double deliveryHours;
    private Instant deliveryDate;
}
