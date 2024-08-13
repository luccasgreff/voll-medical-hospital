package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.UserEntity;
import com.hosp.med.voll.domain.model.dto.LoginDTO;
import com.hosp.med.voll.domain.model.dto.LoginResponseDTO;
import com.hosp.med.voll.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;
    public LoginResponseDTO registerUser(LoginDTO body) {

        var passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = new UserEntity();
        user.setLogin(body.getLogin());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        repository.save(user);

        log.info("User {} is now registered", body.getLogin());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(user.getId());
        response.setLogin(user.getLogin());
        response.setPassword("Encrypted password");

        return response;
    }
}
