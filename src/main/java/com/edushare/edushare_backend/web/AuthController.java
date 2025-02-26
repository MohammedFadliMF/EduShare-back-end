package com.edushare.edushare_backend.web;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles(){
         List<String> roles = new ArrayList<>();
         roles.add("Admin");
         roles.add("User");
         roles.add("Moderator");
        return new ResponseEntity<List<String>>(roles,HttpStatus.OK);
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
}