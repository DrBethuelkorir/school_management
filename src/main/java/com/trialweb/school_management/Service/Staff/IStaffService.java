package com.trialweb.school_management.Service.Staff;

import com.trialweb.school_management.Dtos.StaffDto;
import com.trialweb.school_management.Responses.StaffResponse;

public interface IStaffService {
    StaffResponse getAllStaff();
    StaffResponse getStaffById(Long id);
    StaffResponse registerStaff(StaffDto staffDto);
    StaffResponse updateStaff(StaffDto staffDto);
    StaffResponse deleteStaff(Long id);
}
