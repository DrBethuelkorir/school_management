package com.trialweb.school_management.Service.Teacher;

import com.trialweb.school_management.Dtos.TeacherDto;
import com.trialweb.school_management.Responses.TeacherResponse;

public interface ITeacherService {
    TeacherResponse getAllTeachers();
    TeacherResponse getTeacherById(Long id);
    TeacherResponse registerTeacher(TeacherDto teacherDto);
    TeacherResponse updateTeacher(TeacherDto teacherDto);
    TeacherResponse deleteTeacher(Long id);
}