package com.trialweb.school_management.Service.Roles;

import com.trialweb.school_management.Dtos.RolesDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.Roles;
import com.trialweb.school_management.Repositories.RolesRepository;
import com.trialweb.school_management.Responses.RolesResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService implements IRoles {

    private final RolesRepository rolesRepository;

    @Override
    public RolesResponse getAllRoles() {
        RolesResponse response = new RolesResponse();

        try {
            List<Roles> roles = rolesRepository.findAll();
            List<RolesDto> rolesDto =  roles.stream()
                    .map(Utils::mapRoleEntityToRolesDto)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("Roles retrieved successfully");
            response.setRolesDtoList(rolesDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    public RolesResponse getRoleById(Long id) {
        RolesResponse response = new RolesResponse();

        try {
            Roles role = rolesRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Role not found with id: " + id));

            RolesDto rolesDto = Utils.mapRoleEntityToRolesDto(role);

            response.setStatusCode(200);
            response.setMessage("Role found successfully");
            response.setRolesDto(rolesDto);
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
    public RolesResponse createRole(RolesDto rolesDto) {
        RolesResponse response = new RolesResponse();

        try {
            if (rolesRepository.existsByRoleName(rolesDto.getRoleName())) {
                throw new CustomExeption("Role already exists: " + rolesDto.getRoleName());
            }

            Roles role = new Roles();
            role.setRoleName(rolesDto.getRoleName());

            Roles savedRole = rolesRepository.save(role);

            RolesDto savedRolesDto = Utils.mapRoleEntityToRolesDto(savedRole);

            response.setStatusCode(201);
            response.setMessage("Role created successfully");
            response.setRolesDto(savedRolesDto);
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
    public RolesResponse updateRole(RolesDto rolesDto) {
        RolesResponse response = new RolesResponse();

        try {
            Roles existingRole = rolesRepository.findById(rolesDto.getId())
                    .orElseThrow(() -> new CustomExeption("Role not found with id: " + rolesDto.getId()));

            existingRole.setRoleName(rolesDto.getRoleName());

            Roles updatedRole = rolesRepository.save(existingRole);

            RolesDto updatedRolesDto = Utils.mapRoleEntityToRolesDto(updatedRole);

            response.setStatusCode(200);
            response.setMessage("Role updated successfully");
            response.setRolesDto(updatedRolesDto);
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
    public RolesResponse deleteRole(Long id) {
        RolesResponse response = new RolesResponse();

        try {
            Roles role = rolesRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Role not found with id: " + id));

            rolesRepository.delete(role);

            response.setStatusCode(200);
            response.setMessage("Role deleted successfully");
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