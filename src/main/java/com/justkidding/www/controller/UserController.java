package com.justkidding.www.controller;

import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.model.User;
import com.justkidding.www.service.UserService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<User>> updateUserById (@PathVariable Long id, @RequestBody RegisterDto registerDto) {
        User user = this.userService.updateUserById(id, registerDto);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated a user!!! 🎉🎉🎉", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to update a user!!! 😔💔💔", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser (@PathVariable Long id) {
        return this.userService.deleteUserById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully delete a user!!! 🎉🎉🎉", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete a user!!! 😔💔💔", false));
    }


}
