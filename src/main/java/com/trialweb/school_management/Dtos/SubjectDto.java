package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Departments;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Models.Teachers;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDto {
    private Long id;
    private String subjectName;
    private String subjectCode;
    private List<StudentDto> students;
    private List<TeacherDto> teachers;
    private DepartmentDto departments;
}
