package com.trialweb.school_management.Service.Subjects;

import com.trialweb.school_management.Dtos.SubjectDto;
import com.trialweb.school_management.Models.Subjects;
import com.trialweb.school_management.Repositories.SubjectsRepository;
import com.trialweb.school_management.Responses.SubjectResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService implements ISubjectService {

    private final SubjectsRepository subjectRepository;

    @Override
    public SubjectResponse getAllSubjects() {
        SubjectResponse response = new SubjectResponse();
        try {
            List<Subjects> subjects = subjectRepository.findAll();

            // Using Utils method to map each subject
            List<SubjectDto> subjectDtoList = subjects.stream()
                    .map(Utils::mapSubjectEntityToSubjectDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Subjects retrieved successfully");
            response.setSubjectDtoList(subjectDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving subjects: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SubjectResponse getSubjectById(int id) {
        SubjectResponse response = new SubjectResponse();
        try {
            Subjects subject = subjectRepository.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

            // Using Utils method to convert to DTO
            SubjectDto subjectDto = Utils.mapSubjectEntityToSubjectDto(subject);

            response.setStatusCode(200);
            response.setMessage("Subject retrieved successfully");
            response.setSubjectDto(subjectDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving subject: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SubjectResponse addSubject(SubjectDto subjectDto) {
        SubjectResponse response = new SubjectResponse();
        try {
            // Validate if subject code already exists
            if (subjectRepository.existsBySubjectCode(subjectDto.getSubjectCode())) {
                response.setStatusCode(400);
                response.setMessage("Subject with code " + subjectDto.getSubjectCode() + " already exists");
                return response;
            }

            // Convert DTO to Entity
            Subjects subject = new Subjects();
            subject.setSubjectName(subjectDto.getSubjectName());
            subject.setSubjectCode(subjectDto.getSubjectCode());

            // Save the subject
            Subjects savedSubject = subjectRepository.save(subject);

            // Convert back to DTO using Utils
            SubjectDto savedSubjectDto = Utils.mapSubjectEntityToSubjectDto(savedSubject);

            response.setStatusCode(201);
            response.setMessage("Subject added successfully");
            response.setSubjectDto(savedSubjectDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding subject: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SubjectResponse updateSubject(SubjectDto subjectDto) {
        SubjectResponse response = new SubjectResponse();
        try {
            if (subjectDto.getId() == null) {
                response.setStatusCode(400);
                response.setMessage("Subject ID is required for update");
                return response;
            }

            Subjects existingSubject = subjectRepository.findById(subjectDto.getId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectDto.getId()));

            // Check if subject code is being changed and if new code already exists
            if (!existingSubject.getSubjectCode().equals(subjectDto.getSubjectCode()) &&
                    subjectRepository.existsBySubjectCode(subjectDto.getSubjectCode())) {
                response.setStatusCode(400);
                response.setMessage("Subject with code " + subjectDto.getSubjectCode() + " already exists");
                return response;
            }

            // Update fields
            existingSubject.setSubjectName(subjectDto.getSubjectName());
            existingSubject.setSubjectCode(subjectDto.getSubjectCode());

            // Save the updated subject
            Subjects updatedSubject = subjectRepository.save(existingSubject);

            // Convert to DTO using Utils
            SubjectDto updatedSubjectDto = Utils.mapSubjectEntityToSubjectDto(updatedSubject);

            response.setStatusCode(200);
            response.setMessage("Subject updated successfully");
            response.setSubjectDto(updatedSubjectDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating subject: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SubjectResponse deleteSubject(int id) {
        SubjectResponse response = new SubjectResponse();
        try {
            Subjects subject = subjectRepository.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

            subjectRepository.delete(subject);

            response.setStatusCode(200);
            response.setMessage("Subject deleted successfully");
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting subject: " + e.getMessage());
        }
        return response;
    }

    // Additional useful methods using Utils

    @Override
    public SubjectResponse getSubjectByCode(String subjectCode) {
        SubjectResponse response = new SubjectResponse();
        try {
            Subjects subject = subjectRepository.findBySubjectCode(subjectCode)
                    .orElseThrow(() -> new RuntimeException("Subject not found with code: " + subjectCode));

            // Using Utils method
            SubjectDto subjectDto = Utils.mapSubjectEntityToSubjectDto(subject);

            response.setStatusCode(200);
            response.setMessage("Subject retrieved successfully");
            response.setSubjectDto(subjectDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving subject: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SubjectResponse getSubjectWithAllRelationships(int id) {
        SubjectResponse response = new SubjectResponse();
        try {
            Subjects subject = subjectRepository.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

            // Combine all Utils methods
            SubjectDto subjectDto = Utils.mapSubjectEntityToSubjectDto(subject);

            if (subject.getStudents() != null && !subject.getStudents().isEmpty()) {
                subjectDto.setStudents(subject.getStudents().stream()
                        .map(Utils::mapStudentEntityToStudentDto)
                        .collect(Collectors.toList()));
            }

            if (subject.getTeachers() != null && !subject.getTeachers().isEmpty()) {
                subjectDto.setTeachers(subject.getTeachers().stream()
                        .map(Utils::mapTeacherEntityToTeacherDto)
                        .collect(Collectors.toList()));
            }

            if (subject.getDepartments() != null) {
                subjectDto.setDepartments(Utils.mapDepartmentEntityToDepartmentDto(subject.getDepartments()));
            }

            response.setStatusCode(200);
            response.setMessage("Subject with all relationships retrieved successfully");
            response.setSubjectDto(subjectDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving subject with all relationships: " + e.getMessage());
        }
        return response;
    }
}