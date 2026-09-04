package com.trialweb.school_management.Service.Dorm;

import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Responses.DormResponse;

public interface IDormService {
    DormResponse createDorm(DormDto dormDto);

    // Read
    DormResponse getDormById(Long id);
    DormResponse getAllDorms();

    // Update
    DormResponse updateDorm(Long id, DormDto dormDto);

    // Delete
    DormResponse deleteDorm(Long id);
}
