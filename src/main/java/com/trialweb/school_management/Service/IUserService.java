package com.trialweb.school_management.Service;

import com.trialweb.school_management.Dtos.LoginDto;
import com.trialweb.school_management.Responses.LoginResponse;
import com.trialweb.school_management.Responses.RegisterResponse;
import com.trialweb.school_management.Responses.UserResponse;

public interface IUserService {
        RegisterResponse registerUser(com.trialweb.school_management.Models.User user);
        LoginResponse loginUser(LoginDto loginDto);
        UserResponse getAllUsers();
        UserResponse deleteUSer(Long userId);
        UserResponse getUserById(Long userId);
        UserResponse getMyInfo(String email);
}
