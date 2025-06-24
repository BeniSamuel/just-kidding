package com.justkidding.www.dto;

import com.justkidding.www.enums.Role;
import lombok.Getter;

@Getter
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private Role role;
    private int phone;
}
