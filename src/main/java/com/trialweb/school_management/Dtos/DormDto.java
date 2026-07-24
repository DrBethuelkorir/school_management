package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Models.Students;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DormDto {
    private Long id;
    private String name;
    private Long capacity;
    private List<Students> students;
}
