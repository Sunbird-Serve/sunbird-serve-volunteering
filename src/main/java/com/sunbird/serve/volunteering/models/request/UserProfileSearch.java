package com.sunbird.serve.volunteering.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSearch {
    private Integer offset;
    private Integer limit;
    private Filters filters;
}
