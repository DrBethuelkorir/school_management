package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Models.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentsResponse {
    private int statusCode;
    private String message;
    private ParentDto parentDto;
    private List<ParentDto> parentDtoList;
}
