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
public class Response{
	private String schema;
	private String type;
	private String title;
	private Definitions definitions;
	private OsConfig osConfig;
	private Properties properties;
	private List<String> required;
}