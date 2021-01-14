package com.neoblacko.authentication;

import com.neoblacko.model.User;
import com.neoblacko.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var roles = new ArrayList<SimpleGrantedAuthority>();
        var userEmail = authentication.getName();
        var userPassword = authentication.getCredentials().toString();

        var user = userServiceImpl.getUser(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Неверный логин или пароль");
        }

        if (!bcryptPasswordEncoder.matches(userPassword, user.getUserPassword().trim())) {
            throw new BadCredentialsException(userEmail);
        }

        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().trim()));

        return new UsernamePasswordAuthenticationToken(userEmail, userPassword, roles);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
