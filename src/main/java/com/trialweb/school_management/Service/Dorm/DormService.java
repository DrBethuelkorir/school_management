package com.trialweb.school_management.Service.Dorm;

import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Dtos.StudentDto;
import com.trialweb.school_management.Models.Dorm;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Models.Teachers;
import com.trialweb.school_management.Repositories.DormRepository;
import com.trialweb.school_management.Repositories.StudentsRepository;
import com.trialweb.school_management.Repositories.TeachersRepository;
import com.trialweb.school_management.Responses.DormResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DormService implements IDormService {

    private final DormRepository dormRepository;
    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository; // Add this

    // ==================== CREATE ====================

    @Override
    public DormResponse createDorm(DormDto dormDto) {
        DormResponse response = new DormResponse();

        try {
            // Check if dorm name already exists
            if (dormRepository.existsByName(dormDto.getName())) {
                throw new RuntimeException("Dorm name already exists: " + dormDto.getName());
            }

            // Build dorm entity
            Dorm dorm = new Dorm();
            dorm.setName(dormDto.getName());
            dorm.setCapacity(dormDto.getCapacity());
            dorm.setCurrentOccupancy(0L); // Start with 0 students

            // Save dorm
            Dorm savedDorm = dormRepository.save(dorm);
            DormDto savedDormDto = Utils.mapDormEntityToDormDto(savedDorm);

            response.setStatusCode(201);
            response.setMessage("Dorm created successfully");
            response.setDormDto(savedDormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating dorm: " + e.getMessage());
        }

        return response;
    }

    // ==================== READ ====================

    @Override
    public DormResponse getDormById(Long id) {
        DormResponse response = new DormResponse();

        try {
            Dorm dorm = findDormOrThrow(id);
            DormDto dormDto = Utils.mapDormEntityToDormDto(dorm);

            response.setStatusCode(200);
            response.setMessage("Dorm retrieved successfully");
            response.setDormDto(dormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving dorm: " + e.getMessage());
        }

        return response;
    }

    @Override
    public DormResponse getAllDorms() {
        DormResponse response = new DormResponse();

        try {
            List<Dorm> dorms = dormRepository.findAll();
            List<DormDto> dormDtos = dorms.stream()
                    .map(Utils::mapDormEntityToDormDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Dorms retrieved successfully");
            response.setDormsDto(dormDtos);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving dorms: " + e.getMessage());
        }

        return response;
    }

    // ==================== UPDATE ====================

    @Override
    public DormResponse updateDorm(Long id, DormDto dormDto) {
        DormResponse response = new DormResponse();

        try {
            Dorm dorm = findDormOrThrow(id);

            // Update fields if provided
            updateDormFields(dorm, dormDto);

            // Save updated dorm
            Dorm updatedDorm = dormRepository.save(dorm);
            DormDto updatedDormDto = Utils.mapDormEntityToDormDto(updatedDorm);

            response.setStatusCode(200);
            response.setMessage("Dorm updated successfully");
            response.setDormDto(updatedDormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating dorm: " + e.getMessage());
        }

        return response;
    }

    // ==================== DELETE ====================

    @Override
    public DormResponse deleteDorm(Long id) {
        DormResponse response = new DormResponse();

        try {
            Dorm dorm = findDormOrThrow(id);

            // Check if dorm has students
            if (!dorm.getStudents().isEmpty()) {
                throw new RuntimeException("Cannot delete dorm with assigned students. Remove students first.");
            }

            dormRepository.delete(dorm);

            response.setStatusCode(200);
            response.setMessage("Dorm deleted successfully");

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting dorm: " + e.getMessage());
        }

        return response;
    }

    // ==================== STUDENT ASSIGNMENT METHODS (NEW) ====================

    /**
     * Assign a student to a dorm
     * This automatically increments currentOccupancy by 1
     */
    @Override
    @Transactional
    public synchronized DormResponse assignStudentToDorm(Long studentId, Long dormId) {
        DormResponse response = new DormResponse();

        try {
            // Find student and dorm
            Students student = findStudentOrThrow(studentId);
            Dorm dorm = findDormOrThrow(dormId);

            // Check if student is already assigned to a dorm
            if (student.getDorm() != null) {
                throw new RuntimeException("Student already assigned to a dorm. Remove them first.");
            }

            // Check if dorm has capacity
            if (dorm.getCurrentOccupancy() >= dorm.getCapacity()) {
                throw new RuntimeException("Dorm is full! Capacity: " + dorm.getCapacity() +
                        ", Current: " + dorm.getCurrentOccupancy());
            }

            // Assign student to dorm
            student.setDorm(dorm);
            dorm.getStudents().add(student);

            // Increment occupancy
            dorm.setCurrentOccupancy(dorm.getCurrentOccupancy() + 1);

            // Save both entities
            studentsRepository.save(student);
            Dorm updatedDorm = dormRepository.save(dorm);

            // Prepare response
            DormDto dormDto = Utils.mapDormEntityToDormDto(updatedDorm);
            response.setStatusCode(200);
            response.setMessage("Student assigned to dorm successfully. New occupancy: " + updatedDorm.getCurrentOccupancy());
            response.setDormDto(dormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error assigning student to dorm: " + e.getMessage());
        }

        return response;
    }

    /**
     * Remove a student from their current dorm
     * This automatically decrements currentOccupancy by 1
     */
    @Override
    @Transactional
    public DormResponse removeStudentFromDorm(Long studentId) {
        DormResponse response = new DormResponse();

        try {
            // Find student
            Students student = findStudentOrThrow(studentId);

            // Check if student is assigned to any dorm
            if (student.getDorm() == null) {
                throw new RuntimeException("Student is not assigned to any dorm.");
            }

            // Get the dorm
            Dorm dorm = student.getDorm();

            // Remove student from dorm
            dorm.getStudents().remove(student);
            student.setDorm(null);

            // Decrement occupancy
            dorm.setCurrentOccupancy(dorm.getCurrentOccupancy() - 1);

            // Save both entities
            studentsRepository.save(student);
            Dorm updatedDorm = dormRepository.save(dorm);

            // Prepare response
            DormDto dormDto = Utils.mapDormEntityToDormDto(updatedDorm);
            response.setStatusCode(200);
            response.setMessage("Student removed from dorm successfully. New occupancy: " + updatedDorm.getCurrentOccupancy());
            response.setDormDto(dormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing student from dorm: " + e.getMessage());
        }

        return response;
    }

    /**
     * Transfer a student from one dorm to another
     */
    @Override
    @Transactional
    public DormResponse transferStudentToDorm(Long studentId, Long newDormId) {
        DormResponse response = new DormResponse();

        try {
            // Find student and new dorm
            Students student = findStudentOrThrow(studentId);
            Dorm newDorm = findDormOrThrow(newDormId);

            // Check if student is assigned to any dorm
            if (student.getDorm() == null) {
                throw new RuntimeException("Student is not in any dorm. Please assign them first.");
            }

            // Check if new dorm has capacity
            if (newDorm.getCurrentOccupancy() >= newDorm.getCapacity()) {
                throw new RuntimeException("New dorm is full! Capacity: " + newDorm.getCapacity() +
                        ", Current: " + newDorm.getCurrentOccupancy());
            }

            // Get the old dorm
            Dorm oldDorm = student.getDorm();

            // Remove from old dorm
            oldDorm.getStudents().remove(student);
            oldDorm.setCurrentOccupancy(oldDorm.getCurrentOccupancy() - 1);

            // Add to new dorm
            student.setDorm(newDorm);
            newDorm.getStudents().add(student);
            newDorm.setCurrentOccupancy(newDorm.getCurrentOccupancy() + 1);

            // Save all changes
            studentsRepository.save(student);
            dormRepository.save(oldDorm);
            Dorm updatedNewDorm = dormRepository.save(newDorm);

            // Prepare response
            DormDto dormDto = Utils.mapDormEntityToDormDto(updatedNewDorm);
            response.setStatusCode(200);
            response.setMessage("Student transferred successfully. New dorm occupancy: " + updatedNewDorm.getCurrentOccupancy());
            response.setDormDto(dormDto);

        } catch (RuntimeException e) {
            response.setStatusCode(409);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error transferring student: " + e.getMessage());
        }

        return response;
    }

    /**
     * Get all students in a specific dorm
     */
    @Override
    public DormResponse getStudentsInDorm(Long dormId) {
        DormResponse response = new DormResponse();

        try {
            Dorm dorm = findDormOrThrow(dormId);
            List<Students> students = dorm.getStudents();
            List<StudentDto> studentDto = students.stream()
                            .map(Utils::mapStudentEntityToStudentDto)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("Students retrieved successfully. Total: " + students.size());
            response.setStudentsDto(studentDto); // You'll need to add this field to DormResponse

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving students: " + e.getMessage());
        }

        return response;
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private void updateDormFields(Dorm dorm, DormDto dormDto) {
        // Update name if provided
        if (dormDto.getName() != null && !dormDto.getName().isEmpty()) {
            validateUniqueDormName(dormDto.getName(), dorm.getId());
            dorm.setName(dormDto.getName());
        }

        // Update capacity if provided
        if (dormDto.getCapacity() > 0) {
            // Check if new capacity is less than current occupancy
            if (dormDto.getCapacity() < dorm.getCurrentOccupancy()) {
                throw new RuntimeException("Cannot reduce capacity below current occupancy: " + dorm.getCurrentOccupancy());
            }
            dorm.setCapacity(dormDto.getCapacity());
        }
    }

    private void validateUniqueDormName(String name, Long currentDormId) {
        dormRepository.findByName(name).ifPresent(existingDorm -> {
            if (!existingDorm.getId().equals(currentDormId)) {
                throw new RuntimeException("Dorm name already exists: " + name);
            }
        });
    }

    private Dorm findDormOrThrow(Long id) {
        return dormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dorm not found with id: " + id));
    }

    private Teachers findTeacherOrThrow(Long id) {
        return teachersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    private Students findStudentOrThrow(Long id) {
        return studentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
}