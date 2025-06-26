package com.justkidding.www.service;

import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public User registerUser (RegisterDto registerDto) {
        return this.userService.createUser(registerDto);
    }

    public String loginUser () {
        return "token";
    }
}
