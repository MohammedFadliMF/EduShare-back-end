package com.edushare.edushare_backend.web;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.edushare.edushare_backend.entities.Teacher;
import com.edushare.edushare_backend.entities.Student;
import com.edushare.edushare_backend.enums.Role;
import com.edushare.edushare_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
    }

  @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

  @PostMapping("/login")
    public Map<String,String> login(String username, String password){
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(username, password)
      );

        Instant instant = Instant.now();

        String scope=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect((Collectors.joining(" ")));

        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope",scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );

        String jwt= jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        return Map.of("access-token",jwt);
    }

    @PostMapping("/register/teacher")
    public Teacher registerProfessor(@RequestBody Teacher teacher) {
        teacher.setRole(Role.TEACHER); // Assign TEACHER role
        return (Teacher) userService.registerUser(teacher);
    }

    @PostMapping("/register/student")
    public Student registerStudent(@RequestBody Student student) {
        student.setRole(Role.STUDENT); // Assign STUDENT role
        return (Student) userService.registerUser(student);
    }
}