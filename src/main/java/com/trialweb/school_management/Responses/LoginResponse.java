package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;



@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends UserResponse{
    private String token;
    private Collection<GrantedAuthority> role;
    private String expirationTime;
    private String message;

}
