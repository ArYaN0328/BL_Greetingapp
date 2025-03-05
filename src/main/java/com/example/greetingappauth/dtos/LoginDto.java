package com.example.greetingappauth.dtos;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "password should be of 8 characters")
    private String password;

    public String getEmail() {

        return email;
    }

    public String getPassword() {
        return password;
    }



}
