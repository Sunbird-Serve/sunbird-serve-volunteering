package com.sunbird.serve.volunteering.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User { 

   private String osUpdatedAt;
   private List<String> role;
   private String osCreatedAt;
   private String osUpdatedBy;
   private String osCreatedBy;
   private String agencyId;
   private String osid;
   private UserContactDetails userContactDetails;
   private List<String> osOwner;
   private IdentityDetails identityDetails;
   private String status;

}