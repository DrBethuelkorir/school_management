package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.*;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private List<Roles> roles;
    private ClassesDto className;
    private StreamDto streamName;
    private DormDto dormName;
    private ParentDto parentName;
    private List<SubjectDto> subjects;
}
