package com.trialweb.school_management.Service.Studnets;

import com.trialweb.school_management.Dtos.StudentDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Repositories.StudentsRepository;
import com.trialweb.school_management.Responses.StudentResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentsService {

    private final StudentsRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentResponse getAllStudents() {
        StudentResponse response = new StudentResponse();

        try {
            List<Students> students = studentRepository.findAll();
            List<StudentDto> studentDtos = students.stream()
                    .map(Utils::mapStudentEntityToStudentDto)
                    .toList();
            response.setMessage("Students found");
            response.setStatusCode(200);
            response.setStudentDtos(studentDtos);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        StudentResponse response = new StudentResponse();

        try {
            Students student = studentRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Student not found with id: " + id));

            StudentDto studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setEmail(student.getEmail());
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            studentDto.setAge(student.getAge());
            studentDto.setAdm(student.getAdm());

            response.setStatusCode(200);
            response.setMessage("Student found successfully");
            response.setStudentsDto(studentDto);
        } catch (CustomExeption e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StudentResponse registerStudent(StudentDto studentDto) {
        StudentResponse response = new StudentResponse();

        try {
            if (studentRepository.existsByEmail(studentDto.getEmail())) {
                throw new CustomExeption("Email already exists: " + studentDto.getEmail());
            }

            Students student = new Students();
            student.setEmail(studentDto.getEmail());
            student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            student.setAge(studentDto.getAge());
            student.setAdm(studentDto.getAdm());

            Students savedStudent = studentRepository.save(student);
            StudentDto savedStudentDto = Utils.mapStudentEntityToStudentDto(savedStudent);

            response.setStatusCode(201);
            response.setMessage("Student registered successfully");
            response.setStudentsDto(savedStudentDto);
        } catch (CustomExeption e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StudentResponse updateStudent(StudentDto studentDto) {
        StudentResponse response = new StudentResponse();

        try {
            Students existingStudent = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new CustomExeption("Student not found with id: " + studentDto.getId()));

            existingStudent.setFirstName(studentDto.getFirstName());
            existingStudent.setLastName(studentDto.getLastName());
            existingStudent.setAge(studentDto.getAge());
            existingStudent.setAdm(studentDto.getAdm());

            if (studentDto.getPassword() != null && !studentDto.getPassword().isEmpty()) {
                existingStudent.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            }

            Students updatedStudent = studentRepository.save(existingStudent);
            StudentDto updatedStudentDto = Utils.mapStudentEntityToStudentDto(updatedStudent);

            response.setStatusCode(200);
            response.setMessage("Student updated successfully");
            response.setStudentsDto(updatedStudentDto);
        } catch (CustomExeption e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StudentResponse deleteStudent(Long id) {
        StudentResponse response = new StudentResponse();

        try {
            Students student = studentRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Student not found with id: " + id));

            studentRepository.delete(student);

            response.setStatusCode(200);
            response.setMessage("Student deleted successfully");
        } catch (CustomExeption e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }
}