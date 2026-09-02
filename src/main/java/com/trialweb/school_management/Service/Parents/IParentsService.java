package com.trialweb.school_management.Service.Parents;

import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Dtos.UserDto;
import com.trialweb.school_management.Responses.ParentsResponse;

public interface IParentsService {
    ParentsResponse getAllParents();
    ParentsResponse getParentById(Long id);
    ParentsResponse registerParent(ParentDto parentDto);
    ParentsResponse updateParent(ParentDto parentDto);
    ParentsResponse deleteParent(Long id);
}
