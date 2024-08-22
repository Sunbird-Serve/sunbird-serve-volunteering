package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateVolHoursRequest {
    private Instant startTime;
    private Instant endTime;
}
