package com.sunbird.serve.volunteering.models.request.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile{
	private String ref;
	private String userId;
	private String type;
	private String title;
	private List<Object> required;
	private Properties properties;
	private String id;
}
