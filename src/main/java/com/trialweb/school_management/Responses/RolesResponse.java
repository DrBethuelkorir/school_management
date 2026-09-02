package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.RolesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponse {
    private int statusCode;
    private String message;
    private RolesDto rolesDto;
    private List<RolesDto> rolesDtoList;
}
