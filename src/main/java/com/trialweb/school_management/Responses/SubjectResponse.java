package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.SubjectDto;
import lombok.Data;

import java.util.List;

@Data
public class SubjectResponse {
    private int statusCode;
    private String message;
    private SubjectDto subjectDto;
    private List<SubjectDto> SubjectDtoList;
}
