package com.justkidding.www.dto;

import com.justkidding.www.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private Role role;
    private int phone;
}
