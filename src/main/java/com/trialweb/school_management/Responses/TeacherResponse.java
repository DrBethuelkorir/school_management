package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Dtos.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherResponse {
    private int statusCode;
    private String message;
    private TeacherDto teacherDto;
    private List<TeacherDto> teacherDtoList;
}
