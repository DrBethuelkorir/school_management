package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.ClassesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponses {
    private int statusCode;
    private String message;
    private ClassesDto classesDto;
    private List<ClassesDto> classesDtoList;
}
