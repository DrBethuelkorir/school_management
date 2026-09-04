package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Dtos.StudentDto;
import lombok.Data;
import java.util.List;

@Data
public class DormResponse {
    private int statusCode;
    private String message;
    private DormDto dormDto;
    private List<DormDto> dormsDto;
    private List<StudentDto>  studentsDto;
}