package com.sunbird.serve.volunteering.models.request.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {
    private List<String> language;
    private List<String> dayPreferred;
    private List<String> timePreferred;
    private List<String> interestArea;
}
