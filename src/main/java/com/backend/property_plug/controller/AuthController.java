package com.backend.property_plug.controller;

import com.backend.property_plug.dto.*;
import com.backend.property_plug.entity.UserType;
import com.backend.property_plug.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.backend.property_plug.security.JwtUtil;
import com.backend.property_plug.entity.User;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "Endpoints for user authentication and account management")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("Registration successful! Please check your email to verify your account.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Verify user account")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok("Account verified successfully! You can now log in.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }

    @Operation(summary = "Initiate password reset")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        userService.initiatePasswordReset(email);
        return ResponseEntity.ok("If an account with that email exists, we've sent a password reset link.");
    }

    @Operation(summary = "Reset password")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        try {
            if (userService.resetPassword(passwordResetDto)) {
                return ResponseEntity.ok("Password reset successful! You can now log in with your new password.");
            } else {
                return ResponseEntity.badRequest().body("Invalid or expired reset token.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Change password for authenticated user")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam("email") String email, @RequestBody @Valid ChangePasswordDto changePasswordDto) {
        try {
            if (userService.changePassword(email, changePasswordDto)) {
                return ResponseEntity.ok("Password changed successfully!");
            } else {
                return ResponseEntity.badRequest().body("Current password is incorrect.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get available user types")
    @GetMapping("/user-types")
    public ResponseEntity<UserType[]> getUserTypes() {
        return ResponseEntity.ok(UserType.values());
    }

    @Operation(summary = "Authenticate user and return access token and user info")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            List<String> roles = user.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());
            String token = jwtUtil.generateToken(user.getEmail(), roles);
            LoginResponseDto response = new LoginResponseDto(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getUserType() != null ? user.getUserType().name() : null
            );
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}