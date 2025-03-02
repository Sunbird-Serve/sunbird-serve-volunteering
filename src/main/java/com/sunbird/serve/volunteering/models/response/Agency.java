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
public class Agency { 

   private String osUpdatedAt;
   private String website;
   private String establishedDate;
   private String description;
   private String osCreatedAt;
   private String osUpdatedBy;
   private String osCreatedBy;
   private String name;
   private String osid;
   private AgencyContactDetails contactDetails;
   private List<String> osOwner;
   private String agencyType;

}