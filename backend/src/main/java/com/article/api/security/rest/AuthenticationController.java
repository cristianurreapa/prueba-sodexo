package com.article.api.security.rest;

import com.article.api.security.domain.AuthenticationRequest;
import com.article.api.security.domain.AuthenticationResponse;
import com.article.api.security.domain.RegisterRequest;
import com.article.api.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"http://localhost:4200"})
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value= "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping(value = "/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping(value = "/logout")
    public void logout() {
        service.logout();
    }


}
