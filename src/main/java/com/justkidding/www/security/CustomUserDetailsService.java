package com.justkidding.www.security;

import com.justkidding.www.exception.NotFoundException;
import com.justkidding.www.model.User;
import com.justkidding.www.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUserByEmail(username);
        if (user != null) {
            return new CustomUserDetails(user);
        }
        throw new NotFoundException("Sorry user not found!!!");
    }
}
