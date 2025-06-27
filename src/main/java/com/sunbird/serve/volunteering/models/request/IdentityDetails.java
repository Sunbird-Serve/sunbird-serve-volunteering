package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDetails {

   private String gender;
   private String dob;
   private String name;
   private String fullname;

   @JsonProperty("Nationality")
   private String Nationality;

}