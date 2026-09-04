package com.trialweb.school_management.Service.Departments;

import com.trialweb.school_management.Dtos.DepartmentDto;
import com.trialweb.school_management.Dtos.StaffDto;
import com.trialweb.school_management.Dtos.SubjectDto;
import com.trialweb.school_management.Dtos.TeacherDto;
import com.trialweb.school_management.Models.Departments;
import com.trialweb.school_management.Models.Staff;
import com.trialweb.school_management.Models.Subjects;
import com.trialweb.school_management.Models.Teachers;
import com.trialweb.school_management.Repositories.DepartmentRepository;
import com.trialweb.school_management.Repositories.StaffRepository;
import com.trialweb.school_management.Repositories.SubjectsRepository;
import com.trialweb.school_management.Repositories.TeachersRepository;
import com.trialweb.school_management.Responses.DepartmentResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeachersRepository teachersRepository;
    private final SubjectsRepository subjectsRepository;
    private final StaffRepository staffRepository;

    // ==================== CREATE ====================

    @Override
    public DepartmentResponse createDepartment(DepartmentDto departmentDto) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            // Check if department name already exists
            if (departmentRepository.existsByDepartmentName(departmentDto.getDepartmentName())) {
                throw new RuntimeException("Department name already exists: " + departmentDto.getDepartmentName());
            }

            // Build department entity
            Departments department = new Departments();
            department.setDepartmentName(departmentDto.getDepartmentName());

            // Set description if provided
            if (departmentDto.getDescription() != null) {
                department.setDescription(departmentDto.getDescription());
            }

            // Save department
            Departments savedDepartment = departmentRepository.save(department);
            DepartmentDto savedDepartmentDto =Utils.mapDepartmentEntityToDepartmentDto(savedDepartment);

            response.setStatusCode(201);
            response.setMessage("Department created successfully");
            response.setDepartmentDto(savedDepartmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating department: " + e.getMessage());
        }

        return response;
    }

    // ==================== READ ====================

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(id);
            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(department);

            response.setStatusCode(200);
            response.setMessage("Department retrieved successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving department: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse getAllDepartments() {
        DepartmentResponse response = new DepartmentResponse();

        try {
            List<Departments> departments = departmentRepository.findAll();
            List<DepartmentDto> departmentDto = departments.stream()
                    .map(Utils::mapDepartmentEntityToDepartmentDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Departments retrieved successfully");
            response.setDepartmentsDto(departmentDto);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving departments: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse searchDepartments(String searchTerm) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            List<Departments> departments = departmentRepository.searchDepartments(searchTerm);
            List<DepartmentDto> departmentDtos = departments.stream()
                    .map(Utils::mapDepartmentEntityToDepartmentDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Search results: " + departments.size() + " departments found");
            response.setDepartmentsDto(departmentDtos);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error searching departments: " + e.getMessage());
        }

        return response;
    }

    // ==================== UPDATE ====================

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentDto departmentDto) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(id);

            // Update fields if provided
            if (departmentDto.getDepartmentName() != null && !departmentDto.getDepartmentName().isEmpty()) {
                // Check if new name already exists (excluding current department)
                departmentRepository.findByDepartmentName(departmentDto.getDepartmentName())
                        .ifPresent(existing -> {
                            if (!existing.getId().equals(id)) {
                                throw new RuntimeException("Department name already exists: " + departmentDto.getDepartmentName());
                            }
                        });
                department.setDepartmentName(departmentDto.getDepartmentName());
            }

            if (departmentDto.getDescription() != null) {
                department.setDescription(departmentDto.getDescription());
            }

            // Save updated department
            Departments updatedDepartment = departmentRepository.save(department);
            DepartmentDto updatedDepartmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);

            response.setStatusCode(200);
            response.setMessage("Department updated successfully");
            response.setDepartmentDto(updatedDepartmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating department: " + e.getMessage());
        }

        return response;
    }

    // ==================== DELETE ====================

    @Override
    public DepartmentResponse deleteDepartment(Long id) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(id);

            // Check if department has teachers
            if (department.getTeachers() != null && !department.getTeachers().isEmpty()) {
                throw new RuntimeException("Cannot delete department with assigned teachers. Remove teachers first.");
            }

            // Check if department has subjects
            if (department.getSubjects() != null && !department.getSubjects().isEmpty()) {
                throw new RuntimeException("Cannot delete department with assigned subjects. Remove subjects first.");
            }

            // Check if department has staff
            if (department.getStaff() != null && !department.getStaff().isEmpty()) {
                throw new RuntimeException("Cannot delete department with assigned staff. Remove staff first.");
            }

            departmentRepository.delete(department);

            response.setStatusCode(200);
            response.setMessage("Department deleted successfully");

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting department: " + e.getMessage());
        }

        return response;
    }

    // ==================== ASSIGNMENT METHODS ====================

    @Override
    @Transactional
    public  DepartmentResponse addTeacherToDepartment(Long departmentId, Long teacherId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Teachers teacher = findTeacherOrThrow(teacherId);

            // Check if teacher already has a department
            if (teacher.getDepartments() != null) {
                throw new RuntimeException("Teacher already belongs to: " + teacher.getDepartments().getDepartmentName());
            }

            // Add teacher to department
            teacher.setDepartments(department);
            department.getTeachers().add(teacher);

            // Save both entities
            teachersRepository.save(teacher);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Teacher added to department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding teacher: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public DepartmentResponse removeTeacherFromDepartment(Long departmentId, Long teacherId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Teachers teacher = findTeacherOrThrow(teacherId);

            // Check if teacher belongs to this department
            if (teacher.getDepartments() == null || !teacher.getDepartments().getId().equals(departmentId)) {
                throw new RuntimeException("Teacher is not in this department.");
            }

            // Remove teacher from department
            teacher.setDepartments(null);
            department.getTeachers().remove(teacher);

            // Save both entities
            teachersRepository.save(teacher);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Teacher removed from department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing teacher: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public DepartmentResponse addSubjectToDepartment(Long departmentId, Long subjectId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Subjects subject = findSubjectOrThrow(subjectId);

            // Check if subject already has a department
            if (subject.getDepartments() != null) {
                throw new RuntimeException("Subject already belongs to: " + subject.getDepartments().getDepartmentName());
            }

            // Add subject to department
            subject.setDepartments(department);
            department.getSubjects().add(subject);

            // Save both entities
            subjectsRepository.save(subject);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Subject added to department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding subject: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public DepartmentResponse removeSubjectFromDepartment(Long departmentId, Long subjectId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Subjects subject = findSubjectOrThrow(subjectId);

            // Check if subject belongs to this department
            if (subject.getDepartments() == null || !subject.getDepartments().getId().equals(departmentId)) {
                throw new RuntimeException("Subject is not in this department.");
            }

            // Remove subject from department
            subject.setDepartments(null);
            department.getSubjects().remove(subject);

            // Save both entities
            subjectsRepository.save(subject);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Subject removed from department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing subject: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public DepartmentResponse addStaffToDepartment(Long departmentId, Long staffId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Staff staff = findStaffOrThrow(staffId);

            // Check if staff already has a department
            if (staff.getDepartment() != null) {
                throw new RuntimeException("Staff already belongs to: " + staff.getDepartment().getDepartmentName());
            }

            // Add staff to department
            staff.setDepartment(department);
            department.getStaff().add(staff);

            // Save both entities
            staffRepository.save(staff);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Staff added to department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding staff: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public DepartmentResponse removeStaffFromDepartment(Long departmentId, Long staffId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            Staff staff = findStaffOrThrow(staffId);

            // Check if staff belongs to this department
            if (staff.getDepartment() == null || !staff.getDepartment().getId().equals(departmentId)) {
                throw new RuntimeException("Staff is not in this department.");
            }

            // Remove staff from department
            staff.setDepartment(null);
            department.getStaff().remove(staff);

            // Save both entities
            staffRepository.save(staff);
            Departments updatedDepartment = departmentRepository.save(department);

            DepartmentDto departmentDto = Utils.mapDepartmentEntityToDepartmentDto(updatedDepartment);
            response.setStatusCode(200);
            response.setMessage("Staff removed from department successfully");
            response.setDepartmentDto(departmentDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing staff: " + e.getMessage());
        }

        return response;
    }

    // ==================== DETAILED VIEWS ====================

    @Override
    public DepartmentResponse getTeachersInDepartment(Long departmentId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            List<Teachers> teachers = department.getTeachers();

            List<TeacherDto> teacherDtos = teachers.stream()
                    .map(Utils::mapTeacherEntityToTeacherDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Teachers retrieved successfully. Total: " + teachers.size());
            response.setTeachersDto(teacherDtos);
            response.setTeacherCount(teachers.size());

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving teachers: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse getSubjectsInDepartment(Long departmentId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            List<Subjects> subjects = department.getSubjects();

            List<SubjectDto> subjectDtos = subjects.stream()
                    .map(Utils::mapSubjectEntityToSubjectDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Subjects retrieved successfully. Total: " + subjects.size());
            response.setSubjectsDto(subjectDtos);
            response.setSubjectCount(subjects.size());

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving subjects: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse getStaffInDepartment(Long departmentId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);
            List<Staff> staff = department.getStaff();

            List<StaffDto> staffDtos = staff.stream()
                    .map(Utils::mapStaffEntityToStaffDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Staff retrieved successfully. Total: " + staff.size());
            response.setStaffDto(staffDtos);
            response.setStaffCount(staff.size());

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving staff: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse getDepartmentStatistics(Long departmentId) {
        DepartmentResponse response = new DepartmentResponse();

        try {
            Departments department = findDepartmentOrThrow(departmentId);

            int teacherCount = department.getTeachers() != null ? department.getTeachers().size() : 0;
            int subjectCount = department.getSubjects() != null ? department.getSubjects().size() : 0;
            int staffCount = department.getStaff() != null ? department.getStaff().size() : 0;

            response.setStatusCode(200);
            response.setMessage("Department statistics retrieved successfully");
            response.setDepartmentDto(Utils.mapDepartmentEntityToDepartmentDto(department));
            response.setTeacherCount(teacherCount);
            response.setSubjectCount(subjectCount);
            response.setStaffCount(staffCount);

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving statistics: " + e.getMessage());
        }

        return response;
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private Departments findDepartmentOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    private Teachers findTeacherOrThrow(Long id) {
        return teachersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    private Subjects findSubjectOrThrow(Long id) {
        return subjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    private Staff findStaffOrThrow(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
    }

    }