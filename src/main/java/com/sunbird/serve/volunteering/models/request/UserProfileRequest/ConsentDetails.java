package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDetails {

    private boolean consentGiven;

    @Schema(example = "yyyy-mm-dd format only")
    private String consentDate;

    private String consentDescription;
}
