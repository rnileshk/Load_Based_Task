package com.nilesh.Backed.controller;

import com.nilesh.Backed.dto.LoginRequest;
import com.nilesh.Backed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginRequest login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return new LoginRequest(token, "Login successful");
    }
}