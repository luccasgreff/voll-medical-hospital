package com.hosp.med.voll.mapper.impl;

import com.hosp.med.voll.domain.model.dto.AuthenticationResponseDTO;
import com.hosp.med.voll.mapper.AuthenticationMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapperImpl implements AuthenticationMapper {


    @Override
    public AuthenticationResponseDTO encryptedTokenToResponseDTO(String encryptedToken) {

        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO();
        responseDTO.setToken(encryptedToken);
        return responseDTO;
    }
}
