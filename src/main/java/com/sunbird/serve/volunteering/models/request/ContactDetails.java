package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetails {

   @Email(message = "Email must be a valid email address")
   @Size(max = 255, message = "Email must not exceed 255 characters")
   private String email;
   
   @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Mobile number must be a valid phone number")
   @Size(max = 20, message = "Mobile number must not exceed 20 characters")
   private String mobile;
   
   @Valid
   private Address address;

}