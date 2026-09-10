package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.DepartmentDto;
import com.trialweb.school_management.Dtos.StaffDto;
import com.trialweb.school_management.Dtos.SubjectDto;
import com.trialweb.school_management.Dtos.TeacherDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse {
    private int statusCode;
    private String message;
    private DepartmentDto departmentDto;
    private List<DepartmentDto> departmentsDto;
    private List<TeacherDto> teachersDto;
    private List<SubjectDto> subjectsDto;
    private List<StaffDto> staffDto;

    // Statistics
    private Integer teacherCount;
    private Integer subjectCount;
    private Integer staffCount;
}