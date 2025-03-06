package com.example.greetingappauth.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-secret-key-your-secret-key-your-secret-key"; // Should be at least 256 bits

    private static Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }// Use a strong key

    public static String generateToken(String username) {

        System.out.println("hello jjiiiiii");
        JwtBuilder token = Jwts.builder();
        System.out.println("builder made");
          token=token.setSubject(username);
        System.out.println("subject done");
            token= token.setIssuedAt(new Date());
         System.out.println("issued date done");
            token= token.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
          System.out.println("Expiration date set");
        token = token.signWith(getSigningKey(), SignatureAlgorithm.HS256);
              System.out.println("secret key done");
               String token2=token.compact();
               System.out.println("compact done");
        return token2;
    }
}
