package com.sunbird.serve.volunteering.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RcUserResponse {

    private String id;
    private String ver;
    private Long ets;
    private UserResult result;
    private Params params;
    private String responseCode;
}
