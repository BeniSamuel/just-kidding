package com.justkidding.www.service;

import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers () {
        return this.userRepository.findAll();
    }

    public User getUserById (Long id) {
        return this.userRepository.getUserById(id).orElse(null);
    }

    public User getUserByEmail (String email) {
        return this.userRepository.getUserByEmail(email).orElse(null);
    }

    public User createUser (RegisterDto registerDto) {
        User user = this.getUserByEmail(registerDto.getEmail());
        if (user != null) {
            return null;
        }
        return this.userRepository.save(new User(registerDto.getName(), registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()), registerDto.getPhone(), registerDto.getRole()));
    }

    public User updateUserById (Long id, RegisterDto registerDto) {
        User user = this.getUserById(id);
        if (user != null) {
            user.setName(registerDto.getName());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setPhone(registerDto.getPhone());
            user.setRole(registerDto.getRole());

            return this.userRepository.save(user);
        }
        return null;
    }

    public Boolean deleteUserById (Long id) {
        User user = this.getUserById(id);
        if (user != null) {
            this.userRepository.delete(user);
            return true;
        }
        return false;
    }
}
