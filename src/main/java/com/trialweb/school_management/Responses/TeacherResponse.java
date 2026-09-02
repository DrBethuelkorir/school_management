package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Dtos.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    private int statusCode;
    private String message;
    private TeacherDto teacherDto;
    private List<TeacherDto> teacherDtoList;
}
