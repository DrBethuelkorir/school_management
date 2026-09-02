package com.trialweb.school_management.Service.User;

import com.trialweb.school_management.Dtos.LoginDto;
import com.trialweb.school_management.Responses.LoginResponse;
import com.trialweb.school_management.Responses.RegisterResponse;
import com.trialweb.school_management.Responses.UserResponse;

public interface IUserService {
        LoginResponse loginUser(LoginDto loginDto);
        UserResponse getAllUsers();
        UserResponse deleteUSer(Long userId);
        UserResponse getUserById(Long userId);
        UserResponse getMyInfo(String email);
}
