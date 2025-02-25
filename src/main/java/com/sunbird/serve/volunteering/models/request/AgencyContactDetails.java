package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyContactDetails { 

   private AgencyAddress address;
   private String mobile;
   private String email;

}