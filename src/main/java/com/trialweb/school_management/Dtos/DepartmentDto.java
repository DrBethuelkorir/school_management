package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Subjects;
import com.trialweb.school_management.Models.Teachers;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {
    private Long id;
    private String departmentName;
    private List<TeacherDto> teachers;
    private List<SubjectDto>  subjects;
}
