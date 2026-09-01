package com.trialweb.school_management.Service.User;

import com.trialweb.school_management.Dtos.LoginDto;
import com.trialweb.school_management.Dtos.UserDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.User;
import com.trialweb.school_management.Repositories.UserRepository;
import com.trialweb.school_management.Responses.DeleteResponse;
import com.trialweb.school_management.Responses.LoginResponse;
import com.trialweb.school_management.Responses.RegisterResponse;
import com.trialweb.school_management.Responses.UserResponse;
import com.trialweb.school_management.Utils.JWTUtils;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

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
            registerResponse.setStatusCode(200);
            registerResponse.setMessage("User registered successfully");
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
        LoginResponse loginResponse = new LoginResponse();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new CustomExeption(loginDto.getEmail() + " not found"));
            String token = jwtUtils.generateToken(authentication);
            loginResponse.setStatusCode(200);
            loginResponse.setToken(token);
            loginResponse.setMessage("User logged in successfully");
            loginResponse.setRole((Collection< GrantedAuthority >) authentication.getAuthorities());
            loginResponse.setExpirationTime("7 days");
        } catch (CustomExeption e) {
            loginResponse.setStatusCode(404);
            loginResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            loginResponse.setStatusCode(500);
            loginResponse.setMessage("ERROR OCCURRED" + e.getMessage());
        }

        return loginResponse;
    }

    @Override
    public UserResponse getAllUsers() {
        UserResponse userResponse = new UserResponse();

        try {
            List<User> users = userRepository.findAll();
            List<UserDto> userDto = Utils.mapUsersEntityToUserDtoPlusRoles(users);
            userResponse.setStatusCode(200);
            userResponse.setMessage("Users found successfully");
            userResponse.setUsers(userDto);
        } catch (Exception e) {
            userResponse.setStatusCode(500);
            userResponse.setMessage(e.getMessage() + "error occurred when finding users");
        }
        return userResponse;
    }

    @Override
    public UserResponse deleteUSer(Long userId) {
        DeleteResponse deleteResponse = new DeleteResponse();
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomExeption("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            deleteResponse.setStatusCode(200);
            deleteResponse.setMessage("User deleted successfully");
            deleteResponse.setUser(userDto);
        } catch (CustomExeption e) {
            deleteResponse.setStatusCode(404);
            deleteResponse.setMessage(e.getMessage());}
        catch (Exception e) {
            deleteResponse.setStatusCode(500);
            deleteResponse.setMessage(e.getMessage());
        }
        return deleteResponse;
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
        UserResponse userResponse = new UserResponse();

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomExeption("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            userResponse.setStatusCode(200);
            userResponse.setMessage("User found successfully");
            userResponse.setUser(userDto);
        } catch (CustomExeption e) {
            userResponse.setStatusCode(404);
            userResponse.setMessage(e.getMessage());
        }catch (Exception e) {
            userResponse.setStatusCode(500);
            userResponse.setMessage("ERROR OCCURRED" + e.getMessage());
        }
        return userResponse;
    }
}
