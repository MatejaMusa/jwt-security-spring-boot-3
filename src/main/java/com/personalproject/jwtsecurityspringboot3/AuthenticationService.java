package com.personalproject.jwtsecurityspringboot3;

import com.personalproject.jwtsecurityspringboot3.auth.AuthenticationRequest;
import com.personalproject.jwtsecurityspringboot3.auth.AuthenticationResponse;
import com.personalproject.jwtsecurityspringboot3.auth.RegisterRequest;
import com.personalproject.jwtsecurityspringboot3.config.JwtService;
import com.personalproject.jwtsecurityspringboot3.user.Role;
import com.personalproject.jwtsecurityspringboot3.user.User;
import com.personalproject.jwtsecurityspringboot3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    }
}
