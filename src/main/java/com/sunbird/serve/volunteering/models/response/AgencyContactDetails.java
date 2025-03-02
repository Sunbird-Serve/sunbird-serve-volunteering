package com.sunbird.serve.volunteering.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyContactDetails {

    private String osUpdatedAt;
    private AgencyAddress address;
    private String osCreatedAt;
    private String osUpdatedBy;
    private String mobile;
    private String osCreatedBy;
    private String osid;
    private String email;

}
