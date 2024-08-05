package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.AuthenticationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(target = "token", source = "encryptedToken")
    AuthenticationResponseDTO encryptedTokenToResponseDTO(String encryptedToken);


}
