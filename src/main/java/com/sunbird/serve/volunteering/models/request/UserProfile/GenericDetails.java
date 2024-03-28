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
public class GenericDetails{
	private String description;
	private String type;
	private String title;
	private List<Object> required;
	private Properties properties;
	private String id;
	private String ref;
}