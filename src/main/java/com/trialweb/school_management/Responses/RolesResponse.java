package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.RolesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesResponse {
    private int statusCode;
    private String message;
    private RolesDto rolesDto;
    private List<RolesDto> rolesDtoList;
}
