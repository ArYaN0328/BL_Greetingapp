package com.example.greetingappauth.services;



import com.example.greetingappauth.dtos.AuthUserDto;
import com.example.greetingappauth.dtos.LoginDto;
import com.example.greetingappauth.models.AuthUser;
import com.example.greetingappauth.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.greetingappauth.security.JwtUtil;

import java.util.Optional;


@Service
public class AuthUserService {

    AuthUserRepository authUserRepository;
    BCryptPasswordEncoder bcrypt;
    JwtUtil jwtUtil;
    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository,BCryptPasswordEncoder bcrpt,JwtUtil jwtUtil)
    {
        this.authUserRepository=authUserRepository;
        this.bcrypt=bcrpt;
        this.jwtUtil=jwtUtil;
    }

    public String registerUser(AuthUserDto authUserDto)
    {
        if(authUserRepository.findByEmail(authUserDto.getEmail()).isPresent())
        {
            return "Email already exists";
        }

        //Hash the password
        String hashpassword=bcrypt.encode(authUserDto.getPassword());


        AuthUser user=new AuthUser();
        user.setEmail(authUserDto.getEmail());
        user.setPassword(authUserDto.getPassword());
        user.setLastName(authUserDto.getLastName());
        user.setFirstName(authUserDto.getFirstName());
        authUserRepository.save(user);

        return "User registered successfully";




    }

    //login
    public String loginUser(LoginDto lg)
    {
        System.out.println("Searching for user with email: " + lg.getEmail());
        Optional<AuthUser> authOpt = authUserRepository.findByEmail(lg.getEmail());
        System.out.println("User found? " + authOpt.isPresent());

        if(authOpt.isPresent())
        {
            return jwtUtil.generateToken(lg.getEmail());
        }
        else
            return "Invalid email or password";
    }



}
