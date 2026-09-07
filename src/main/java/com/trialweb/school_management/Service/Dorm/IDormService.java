package com.trialweb.school_management.Service.Dorm;

import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Responses.DormResponse;

public interface IDormService {

    DormResponse createDorm(DormDto dormDto);
    DormResponse getDormById(Long id);
    DormResponse getAllDorms();
    DormResponse updateDorm(Long id, DormDto dormDto);
    DormResponse deleteDorm(Long id);

    DormResponse assignStudentToDorm(Long studentId, Long dormId);
    DormResponse removeStudentFromDorm(Long studentId);
    DormResponse transferStudentToDorm(Long studentId, Long newDormId);
    DormResponse getStudentsInDorm(Long dormId);
}