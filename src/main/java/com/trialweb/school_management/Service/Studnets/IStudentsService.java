package com.trialweb.school_management.Service.Studnets;

import com.trialweb.school_management.Dtos.StudentDto;
import com.trialweb.school_management.Responses.StudentResponse;

public interface IStudentsService {
    StudentResponse getAllStudents();
    StudentResponse getStudentById(Long id);
    StudentResponse registerStudent(StudentDto studentDto);
    StudentResponse updateStudent(StudentDto studentDto);
    StudentResponse deleteStudent(Long id);
}
