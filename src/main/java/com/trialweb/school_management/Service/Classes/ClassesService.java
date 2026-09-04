package com.trialweb.school_management.Service.Classes;

import com.trialweb.school_management.Dtos.ClassesDto;
import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Repositories.ClassesRepository;
import com.trialweb.school_management.Responses.ClassResponses;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassesService implements IClassesService {

    private final ClassesRepository classesRepository;

    @Override
    public ClassResponses createClass(ClassesDto classesDto) {
        Classes classes = new Classes();
        classes.setName(classesDto.getName());
        classes.setTotalNumberOfStudents(classesDto.getTotalNumberOfStudents());

        Classes savedClass = classesRepository.save(classes);
        ClassesDto responseDto = Utils.mapClassesEntityToClassesDto(savedClass);

        return new ClassResponses(201, "Class created successfully", responseDto, null);
    }

    @Override
    public ClassResponses getAllClasses() {
        List<Classes> classesList = classesRepository.findAll();
        List<ClassesDto> dtoList = classesList.stream()
                .map(Utils::mapClassesEntityToClassesDto)
                .collect(Collectors.toList());

        return new ClassResponses(200, "Classes retrieved successfully", null, dtoList);
    }

    @Override
    public ClassResponses getClassById(Long id) {
        Classes classes = classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        ClassesDto dto = Utils.mapClassesEntityToClassesDto(classes);
        return new ClassResponses(200, "Class retrieved successfully", dto, null);
    }

    @Override
    public ClassResponses updateClass(Long id, ClassesDto classesDto) {
        Classes existingClass = classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        existingClass.setName(classesDto.getName());
        existingClass.setTotalNumberOfStudents(classesDto.getTotalNumberOfStudents());

        Classes updatedClass = classesRepository.save(existingClass);
        ClassesDto responseDto = Utils.mapClassesEntityToClassesDto(updatedClass);

        return new ClassResponses(200, "Class updated successfully", responseDto, null);
    }

    @Override
    public ClassResponses deleteClass(Long id) {
        Classes classes = classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        classesRepository.delete(classes);
        return new ClassResponses(200, "Class deleted successfully", null, null);
    }

}