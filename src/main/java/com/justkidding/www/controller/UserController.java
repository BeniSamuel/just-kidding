package com.justkidding.www.controller;

import com.justkidding.www.model.User;
import com.justkidding.www.service.UserService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/justkidding/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully retrieved all users!!! 🎉🎉🎉", this.userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById (@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained user by id!!! 🎉🎉🎉", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed obtain user not found!!! 😔💔💔", null));
    }


}
