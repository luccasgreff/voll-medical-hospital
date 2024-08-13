package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.UserEntity;
import com.hosp.med.voll.domain.model.dto.AuthenticationDTO;
import com.hosp.med.voll.domain.model.dto.AuthenticationResponseDTO;
import com.hosp.med.voll.mapper.AuthenticationMapper;
import com.hosp.med.voll.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService service;
    @Autowired
    private AuthenticationConfiguration configuration;
    @Autowired
    private AuthenticationMapper mapper;


    public AuthenticationResponseDTO generateToken(AuthenticationDTO body) throws Exception {

        var token = new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword());
        var configurationManager = configuration.getAuthenticationManager();
        var authentication = configurationManager.authenticate(token);
        var encryptedToken = service.generateToken((UserEntity) authentication.getPrincipal());

        return mapper.encryptedTokenToResponseDTO(encryptedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }
}
