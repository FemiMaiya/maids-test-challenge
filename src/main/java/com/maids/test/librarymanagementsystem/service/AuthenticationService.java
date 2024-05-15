package com.maids.test.librarymanagementsystem.service;

import com.maids.test.librarymanagementsystem.config.JwtService;
import com.maids.test.librarymanagementsystem.dto.authentication.request.AuthenticationRequest;
import com.maids.test.librarymanagementsystem.dto.authentication.request.RegisterRequest;
import com.maids.test.librarymanagementsystem.dto.authentication.response.AuthenticationResponse;
import com.maids.test.librarymanagementsystem.enums.Role;
import com.maids.test.librarymanagementsystem.entity.securityConfig.UserEntity;
import com.maids.test.librarymanagementsystem.exceptions.InternalServerException;
import com.maids.test.librarymanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        try {
            userRepository.save(user);
        }catch (Exception exception) {
            log.info("UNABLE TO SAVE RECORDS TO DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
