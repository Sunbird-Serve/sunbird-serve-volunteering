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
public class UserRequest {

   private List<String> role;
   private String agencyId;
   private ContactDetails contactDetails;
   private IdentityDetails identityDetails;
   private String status;

}