package com.sunbird.serve.volunteering.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyAddress {

    private String osUpdatedAt;
    private String osCreatedAt;
    private String osUpdatedBy;
    private String osCreatedBy;
    private String osid;
    private String state;
    private String street;
    private String pincode;
    private String locality;
    private String plot;
    private String district;
    private String village;
    private String landmark;
}
