package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "The API contains a method for user")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    //TO DO changePassword
    //TO DO forgotPassword
}
