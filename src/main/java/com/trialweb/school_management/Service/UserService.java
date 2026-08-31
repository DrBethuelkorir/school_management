package com.trialweb.school_management.Service;

import com.trialweb.school_management.Dtos.LoginDto;
import com.trialweb.school_management.Dtos.UserDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.User;
import com.trialweb.school_management.Repositories.UserRepository;
import com.trialweb.school_management.Responses.LoginResponse;
import com.trialweb.school_management.Responses.RegisterResponse;
import com.trialweb.school_management.Responses.UserResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerUser(User user) {
        RegisterResponse registerResponse = new RegisterResponse();

        try {
            if(userRepository.existsByEmail(user.getEmail())) {
                throw new CustomExeption(user.getEmail() + " already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User saveduser = userRepository.save(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(saveduser);
            registerResponse.setUser(userDto);

        } catch (CustomExeption e) {
            registerResponse.setMessage(e.getMessage());
        }catch (Exception e) {
            registerResponse.setMessage("Error occurred during registration" +e.getMessage());
        }
        return registerResponse;
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        return null;
    }

    @Override
    public UserResponse getAllUsers() {
        UserResponse userResponse = new UserResponse();

        List<User> user = userRepository.findAll();
        List<UserDto> userDto = Utils.mapUserEntityToUserDtoPlusRoles(user);
        return userResponse;
    }

    @Override
    public UserResponse deleteUSer(Long userId) {

        return null;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        UserResponse userResponse = new UserResponse();
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomExeption("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            userResponse.setUser(userDto);
            userResponse.setMessage("success");
        } catch (CustomExeption e) {
            userResponse.setMessage(e.getMessage());
        }catch (Exception e) {
            userResponse.setMessage("Error occurred during registration" +e.getMessage());
        }
        return userResponse;
    }

    @Override
    public UserResponse getMyInfo(String email) {
        return null;
    }
}
