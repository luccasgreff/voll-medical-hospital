package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.dto.authentication.AuthenticationDTO;
import com.hosp.med.voll.domain.model.dto.authentication.AuthenticationResponseDTO;
import com.hosp.med.voll.domain.model.dto.authentication.LoginDTO;
import com.hosp.med.voll.domain.model.dto.authentication.LoginResponseDTO;
import com.hosp.med.voll.service.AuthenticationService;
import com.hosp.med.voll.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @Transactional
    public ResponseEntity<LoginResponseDTO> registerUser(@RequestBody @Valid LoginDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userService.registerUser(body));
    }

    @PostMapping("/authenticate")
    @Transactional
    public ResponseEntity<AuthenticationResponseDTO> signIn(@RequestBody @Valid AuthenticationDTO body) throws Exception {
        return ResponseEntity.ok(authenticationService.generateToken(body));
    }

}
