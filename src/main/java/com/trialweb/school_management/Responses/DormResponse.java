package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Dtos.StudentDto;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DormResponse {
    private int statusCode;
    private String message;
    private DormDto dormDto;
    private List<DormDto> dormsDto;
    private List<StudentDto>  studentsDto;
}