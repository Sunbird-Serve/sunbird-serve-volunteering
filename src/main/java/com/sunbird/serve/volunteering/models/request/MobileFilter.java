package com.sunbird.serve.volunteering.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileFilter {
    @JsonProperty("contactDetails.mobile")
    private Status contactDetailsMobile;
}
