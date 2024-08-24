package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.authentication.AuthenticationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(target = "token", source = "encryptedToken")
    AuthenticationResponseDTO encryptedTokenToResponseDTO(String encryptedToken);


}
