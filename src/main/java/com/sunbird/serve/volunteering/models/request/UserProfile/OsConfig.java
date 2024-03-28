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
public class OsConfig{
	private List<String> privateFields;
	private List<Object> uniqueIndexFields;
	private List<OwnershipAttributesItem> ownershipAttributes;
	private List<Object> internalFields;
	private List<String> inviteRoles;
	private List<String> systemFields;
	private List<Object> indexFields;
}