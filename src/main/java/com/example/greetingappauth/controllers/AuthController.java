package com.example.greetingappauth.controllers;

import com.example.greetingappauth.dtos.AuthUserDto;
import com.example.greetingappauth.dtos.LoginDto;
import com.example.greetingappauth.dtos.PasswordDto;
import com.example.greetingappauth.services.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService authService;

    public AuthController(AuthUserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthUserDto userDTO) {
        return ResponseEntity.ok(authService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDto loginDTO) {
        return ResponseEntity.ok(authService.loginUser(loginDTO));
    }

    @PutMapping("/forgot/{email}")
    public ResponseEntity<String> forgotpass(@RequestBody PasswordDto pass, @PathVariable("email") String email) {
        System.out.println("hello");
        String response = authService.forgotpass(pass, email);
        if (response.equals("User not found")) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(response);
    }


}
