package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyUpdateRequest {

   private String agencyId;
   private Boolean send;

}