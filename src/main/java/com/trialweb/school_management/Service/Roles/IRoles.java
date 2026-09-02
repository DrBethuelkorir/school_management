package com.trialweb.school_management.Service.Roles;

import com.trialweb.school_management.Dtos.RolesDto;
import com.trialweb.school_management.Responses.RolesResponse;

public interface IRoles {
    RolesResponse getAllRoles();
    RolesResponse getRoleById(Long id);
    RolesResponse createRole(RolesDto rolesDto);
    RolesResponse updateRole(RolesDto rolesDto);
    RolesResponse deleteRole(Long id);
}