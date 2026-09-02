package com.trialweb.school_management.Service.Staff;

import com.trialweb.school_management.Dtos.StaffDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.Departments;
import com.trialweb.school_management.Models.Staff;
import com.trialweb.school_management.Repositories.DepartmentRepository;
import com.trialweb.school_management.Repositories.StaffRepository;
import com.trialweb.school_management.Responses.StaffResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @Override
    public StaffResponse getAllStaff() {
        StaffResponse response = new StaffResponse();

        try {
            List<Staff> staffList = staffRepository.findAll();
            List<StaffDto> staffDto = staffList.stream().map(Utils::mapStaffEntityToStaffDto)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("Staff retrieved successfully");
            response.setStaffDtoList(staffDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public StaffResponse getStaffById(Long id) {
        StaffResponse response = new StaffResponse();

        try {
            Staff staff = staffRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Staff not found with id: " + id));

            StaffDto staffDto = new StaffDto();
            staffDto.setId(staff.getId());
            staffDto.setEmail(staff.getEmail());
            staffDto.setFirstName(staff.getFirstName());
            staffDto.setLastName(staff.getLastName());
            staffDto.setStaffId(staff.getStaffId());
            staffDto.setPosition(staff.getPosition());

            response.setStatusCode(200);
            response.setMessage("Staff found successfully");
            response.setStaffDto(staffDto);
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
    public StaffResponse registerStaff(StaffDto staffDto) {
        StaffResponse response = new StaffResponse();

        try {
            if (staffRepository.existsByEmail(staffDto.getEmail())) {
                throw new CustomExeption("Email already exists: " + staffDto.getEmail());
            }

            Staff staff = new Staff();
            staff.setEmail(staffDto.getEmail());
            staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
            staff.setFirstName(staffDto.getFirstName());
            staff.setLastName(staffDto.getLastName());
            staff.setStaffId(staffDto.getStaffId());
            staff.setPosition(staffDto.getPosition());

            // ✅ BETTER: Use department ID instead of name
            if (staffDto.getDepartment() != null && staffDto.getDepartment().getId() != null) {
                Departments departments = departmentRepository.findById(staffDto.getDepartment().getId())
                        .orElseThrow(() -> new CustomExeption("Department not found "));
                staff.setDepartment(departments);
            }

            Staff savedStaff = staffRepository.save(staff);
            StaffDto savedStaffDto = Utils.mapStaffEntityToStaffDtoPlusDepartment(savedStaff);

            response.setStatusCode(201);
            response.setMessage("Staff registered successfully");
            response.setStaffDto(savedStaffDto);
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
    public StaffResponse updateStaff(StaffDto staffDto) {
        StaffResponse response = new StaffResponse();

        try {
            Staff existingStaff = staffRepository.findById(staffDto.getId())
                    .orElseThrow(() -> new CustomExeption("Staff not found "));

            existingStaff.setFirstName(staffDto.getFirstName());
            existingStaff.setLastName(staffDto.getLastName());
            existingStaff.setStaffId(staffDto.getStaffId());
            existingStaff.setPosition(staffDto.getPosition());

            if (staffDto.getPassword() != null && !staffDto.getPassword().isEmpty()) {
                existingStaff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
            }

            Staff updatedStaff = staffRepository.save(existingStaff);

            StaffDto updatedStaffDto = Utils.mapStaffEntityToStaffDto(updatedStaff);

            response.setStatusCode(200);
            response.setMessage("Staff updated successfully");
            response.setStaffDto(updatedStaffDto);
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
    public StaffResponse deleteStaff(Long id) {
        StaffResponse response = new StaffResponse();

        try {
            Staff staff = staffRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Staff not found with id: " + id));

            staffRepository.delete(staff);

            response.setStatusCode(200);
            response.setMessage("Staff deleted successfully");
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