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
public class ConsentDetails{
	private String description;
	private String type;
	private String title;
	private Properties properties;
	private List<String> required;
	private String id;
	private String ref;
}