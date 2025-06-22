package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDetails {

   @NotBlank(message = "Full name is required")
   @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
   @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only letters and spaces")
   private String fullname;
   
   @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
   private String gender;
   
   private LocalDate dob;
   
   @Size(max = 50, message = "Nationality must not exceed 50 characters")
   @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Nationality must contain only letters and spaces")
   private String Nationality;

}