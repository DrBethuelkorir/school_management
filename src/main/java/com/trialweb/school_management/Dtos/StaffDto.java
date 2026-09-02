package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffDto {
    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String staffId;
    private String position;
    private DepartmentDto department;
}
