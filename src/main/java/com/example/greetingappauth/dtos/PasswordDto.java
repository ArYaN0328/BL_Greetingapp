package com.example.greetingappauth.dtos;

import lombok.Data;

@Data
public class PasswordDto {
    String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
