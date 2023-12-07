package com.sunbird.serve.volunteering.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Params {

   private String resmsgid;
   private String err;
   private String msgid;
   private String errmsg;
   private String status;

}