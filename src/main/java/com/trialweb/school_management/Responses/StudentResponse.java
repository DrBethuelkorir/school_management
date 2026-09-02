package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Dtos.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private int statusCode;
    private String message;
    private StudentDto studentsDto;
    private List<StudentDto> StudentDtoList;
}
