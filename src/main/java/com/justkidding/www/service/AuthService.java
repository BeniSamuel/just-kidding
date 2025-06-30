package com.justkidding.www.service;

import com.justkidding.www.dto.LoginDto;
import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.model.User;
import com.justkidding.www.utils.ApiResponse;
import com.justkidding.www.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public User registerUser (RegisterDto registerDto) {
        return this.userService.createUser(registerDto);
    }

    public ResponseEntity<ApiResponse<String>> loginUser (LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken (loginDto.getEmail(), loginDto.getPassword())
            );
            User user = this.userService.getUserByEmail(loginDto.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully logged in a user!!!", this.jwtUtil.generateToken(user.getEmail())));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false,"Invalid credentials provided check your request!!!", null));
        }
    }
}
