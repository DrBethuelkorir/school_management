package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.*;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String adm;
    private List<Roles> roles;
    private ClassesDto classesDto;
    private StreamDto streamDto;
    private DormDto dormName;
    private ParentDto parentName;
    private List<SubjectDto> subjects;
}
