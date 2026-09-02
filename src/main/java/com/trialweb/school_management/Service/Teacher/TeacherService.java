package com.trialweb.school_management.Service.Teacher;

import com.trialweb.school_management.Dtos.TeacherDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.Teachers;
import com.trialweb.school_management.Repositories.TeachersRepository;
import com.trialweb.school_management.Responses.TeacherResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {

    private final TeachersRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TeacherResponse getAllTeachers() {
        TeacherResponse response = new TeacherResponse();

        try {
            List<Teachers> teachers = teacherRepository.findAll();
            List<TeacherDto> teacherDtos = teachers.stream()
                    .map(Utils::mapTeacherEntityToTeacherDto)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("Teachers retrieved successfully");
            response.setTeacherDtoList(teacherDtos);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        TeacherResponse response = new TeacherResponse();

        try {
            Teachers teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Teacher not found with id: " + id));

            TeacherDto teacherDto = Utils.mapTeacherEntityToTeacherDto(teacher);

            response.setStatusCode(200);
            response.setMessage("Teacher found successfully");
            response.setTeacherDto(teacherDto);
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
    public TeacherResponse registerTeacher(TeacherDto teacherDto) {
        TeacherResponse response = new TeacherResponse();

        try {
            if (teacherRepository.existsByEmail(teacherDto.getEmail())) {
                throw new CustomExeption("Email already exists: " + teacherDto.getEmail());
            }

            Teachers teacher = new Teachers();
            teacher.setEmail(teacherDto.getEmail());
            teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            teacher.setFirstName(teacherDto.getFirstName());
            teacher.setLastName(teacherDto.getLastName());
            teacher.setPhone(teacherDto.getPhone());
            teacher.setTeacherId(teacherDto.getTeacherId());

            Teachers savedTeacher = teacherRepository.save(teacher);

            TeacherDto savedTeacherDto = new TeacherDto();
            savedTeacherDto.setId(savedTeacher.getId());
            savedTeacherDto.setEmail(savedTeacher.getEmail());
            savedTeacherDto.setFirstName(savedTeacher.getFirstName());
            savedTeacherDto.setLastName(savedTeacher.getLastName());
            savedTeacherDto.setPhone(savedTeacher.getPhone());
            savedTeacherDto.setTeacherId(savedTeacher.getTeacherId());

            response.setStatusCode(201);
            response.setMessage("Teacher registered successfully");
            response.setTeacherDto(savedTeacherDto);
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
    public TeacherResponse updateTeacher(TeacherDto teacherDto) {
        TeacherResponse response = new TeacherResponse();

        try {
            Teachers existingTeacher = teacherRepository.findById(teacherDto.getId())
                    .orElseThrow(() -> new CustomExeption("Teacher not found with id: " + teacherDto.getId()));

            existingTeacher.setFirstName(teacherDto.getFirstName());
            existingTeacher.setLastName(teacherDto.getLastName());
            existingTeacher.setPhone(teacherDto.getPhone());
            existingTeacher.setTeacherId(teacherDto.getTeacherId());

            if (teacherDto.getPassword() != null && !teacherDto.getPassword().isEmpty()) {
                existingTeacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            }

            Teachers updatedTeacher = teacherRepository.save(existingTeacher);

            TeacherDto updatedTeacherDto = new TeacherDto();
            updatedTeacherDto.setId(updatedTeacher.getId());
            updatedTeacherDto.setEmail(updatedTeacher.getEmail());
            updatedTeacherDto.setFirstName(updatedTeacher.getFirstName());
            updatedTeacherDto.setLastName(updatedTeacher.getLastName());
            updatedTeacherDto.setPhone(updatedTeacher.getPhone());
            updatedTeacherDto.setTeacherId(updatedTeacher.getTeacherId());

            response.setStatusCode(200);
            response.setMessage("Teacher updated successfully");
            response.setTeacherDto(updatedTeacherDto);
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
    public TeacherResponse deleteTeacher(Long id) {
        TeacherResponse response = new TeacherResponse();

        try {
            Teachers teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Teacher not found with id: " + id));

            teacherRepository.delete(teacher);

            response.setStatusCode(200);
            response.setMessage("Teacher deleted successfully");
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