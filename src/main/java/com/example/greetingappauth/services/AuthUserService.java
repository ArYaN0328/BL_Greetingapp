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
    private final EmailService emailService;
    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository, BCryptPasswordEncoder bcrpt, JwtUtil jwtUtil, EmailService emailService)
    {
        this.authUserRepository=authUserRepository;
        this.bcrypt=bcrpt;
        this.jwtUtil=jwtUtil;
        this.emailService = emailService;
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

        // Send Welcome Email
        String subject = "Welcome to Our Platform!";
        String body = "<h3>Dear " + authUserDto.getFirstName() + ",</h3>"
                + "<p>Thank you for registering with us!</p>"
                + "<p>We're excited to have you onboard.</p>"
                + "<p>Best regards, <br> Your Team</p>";

        emailService.sendEmail(authUserDto.getEmail(), subject, body);


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
            String token= jwtUtil.generateToken(lg.getEmail());
            if(token.length()!=0)
            {
                // Send Login Alert Email
                String subject = "Login Alert - Your Account";
                String body = "<h3>Dear " + authOpt.get().getFirstName() + ",</h3>"
                        + "<p>Your account was just accessed.</p>"
                        + "<p>If this was not you, please reset your password immediately.</p>"
                        + "<p>Best regards, <br> Lauda leleeee</p>";

                emailService.sendEmail(authOpt.get().getEmail(), subject, body);
                return "Login Successfull"+"/n"+token;
            }
        }
        return "Invalid email or password";
    }



}
