package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Models.Teachers;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamDto {
    private Long id;
    private String name;
    private int totalNumberOfStudents;
    private ClassesDto classesDto;
    private TeacherDto teachers;
    private List<StudentDto> students;
}
