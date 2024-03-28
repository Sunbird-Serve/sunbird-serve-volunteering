package com.sunbird.serve.volunteering.models.request.UserProfile;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceChannelId{
	private String type;
}
