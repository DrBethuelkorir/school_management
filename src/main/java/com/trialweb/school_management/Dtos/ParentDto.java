package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Roles;
import com.trialweb.school_management.Models.Students;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentDto {
    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String address;
    private String relationshipWithStudent;
    private List<RolesDto> roles = new ArrayList<>();
    private List<StudentDto> students;
}
