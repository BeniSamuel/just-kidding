package com.justkidding.www.controller;

import com.justkidding.www.dto.LoginDto;
import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.model.User;
import com.justkidding.www.service.AuthService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/justkidding/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser (@RequestBody RegisterDto registerDto) {
        User newUser = this.authService.registerUser(registerDto);
        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully registered a user!!! 🎉🎉🎉", newUser));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to register a user bad request!!!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser (@RequestBody LoginDto loginDto) {
        return this.authService.loginUser(loginDto);
    }
}
