package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.AuthenticationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationMapper {

    AuthenticationResponseDTO encryptedTokenToResponseDTO(String encryptedToken);


}
