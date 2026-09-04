package com.trialweb.school_management.Service.Classes;

import com.trialweb.school_management.Dtos.ClassesDto;
import com.trialweb.school_management.Responses.ClassResponses;

public interface IClassesService {
    ClassResponses createClass(ClassesDto classesDto);
    ClassResponses getAllClasses();
    ClassResponses getClassById(Long id);
    ClassResponses updateClass(Long id, ClassesDto classesDto);
    ClassResponses deleteClass(Long id);
}