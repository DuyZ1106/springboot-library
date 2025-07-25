package com.example.library.controller.auth;

import com.example.library.dto.auth.LoginRequest;
import com.example.library.dto.auth.LoginResponse;
import com.example.library.dto.auth.RegisterRequest;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.response.ApiResponse;
import com.example.library.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Đăng nhập - sinh JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtTokenProvider.generateToken(request.getUsername());

        return ResponseEntity.ok(ApiResponse.success(new LoginResponse(token)));
    }

    /**
     * Đăng ký tài khoản mới
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body((ApiResponse<String>) ApiResponse.<String>error("error.username.exists", "Username already exists"));

        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success("Register success"));
    }

}
