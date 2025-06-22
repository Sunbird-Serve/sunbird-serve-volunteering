package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

   @NotEmpty(message = "Role is required")
   @Size(min = 1, message = "At least one role must be specified")
   private List<String> role;
   
   @Size(max = 100, message = "Agency ID must not exceed 100 characters")
   private String agencyId;
   
   @NotNull(message = "Contact details are required")
   @Valid
   private ContactDetails contactDetails;
   
   @NotNull(message = "Identity details are required")
   @Valid
   private IdentityDetails identityDetails;
   
   @Size(max = 50, message = "Status must not exceed 50 characters")
   private String status;

}