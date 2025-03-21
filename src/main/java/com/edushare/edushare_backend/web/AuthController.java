package com.edushare.edushare_backend.web;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.edushare.edushare_backend.entities.Teacher;
import com.edushare.edushare_backend.entities.Student;
import com.edushare.edushare_backend.enums.Role;
import com.edushare.edushare_backend.exceptions.UserException;
import com.edushare.edushare_backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

  @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
  @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

  @PostMapping("/login")
    public  ResponseEntity<Void> login(String username, String password, HttpServletResponse response){
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

      ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", jwt)
              .httpOnly(true)
              .secure(true)
              .sameSite("Strict")
              .path("/")
              .maxAge(Duration.ofMinutes(10))
              .build();

      return ResponseEntity.ok()
              .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<Map<String, String>> registerProfessor(@RequestBody Teacher teacher) throws UserException {
        teacher.setRole(Role.TEACHER); // Assign TEACHER role
        userService.registerUser(teacher);

        // Authenticate the newly registered user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(teacher.getEmail(), teacher.getPassword())
        );

        // Generate JWT Token
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES)) // Token validity
                .subject(teacher.getEmail()) // User identity
                .claim("scope", scope) // User roles/permissions
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );

        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        // Return response
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Your account has been created successfully!",
                        "userId", teacher.getUserId().toString(),
                        "access-token", jwt
                )
        );
    }

    @PostMapping("/register/student")
    public ResponseEntity<Map<String, String>> registerStudent(@RequestBody Student student) throws UserException {
        student.setRole(Role.STUDENT); // Assign STUDENT role
        userService.registerUser(student);
        
        // Authenticate the newly registered user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(student.getEmail(), student.getPassword())
        );

        // Generate JWT Token
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES)) // Token validity
                .subject(student.getEmail()) // User identity
                .claim("scope", scope) // User roles/permissions
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );

        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        // Return response
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Your account has been created successfully!",
                        "userId", student.getUserId().toString(),
                        "access-token", jwt
                )
        );
    }
}