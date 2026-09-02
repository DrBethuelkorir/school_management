package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Departments;
import com.trialweb.school_management.Models.Roles;
import com.trialweb.school_management.Models.Subjects;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDto {
    private Long id;
    private String email;
    private  String password;
    private String firstName;
    private String lastName;
    private List<Roles> roles;
    private String phone;
    private String teacherId;
    private List<SubjectDto> subjects;
    private DepartmentDto departments;
}
