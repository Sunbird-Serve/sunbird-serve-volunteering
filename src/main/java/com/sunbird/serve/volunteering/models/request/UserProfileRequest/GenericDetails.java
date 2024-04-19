package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericDetails {

    @Schema(example = "High School," +
            " Pre University," +
            " Graduate," +
            " Post Graduate," +
             " Professional Degree")
    private String qualification;

    private String affiliation;

    @Schema(example = "Full Time," +
            " Part Time," +
            " Self Employed," +
            " Homemaker," +
            " Student," +
            " Retired," +
            " Not Employed," +
            " Others")
    private String employmentStatus;

    private String yearsOfExperience;
}