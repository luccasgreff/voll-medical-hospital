package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.GetPatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdatePatientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PatientMapper {


    @Mapping(target = "address.zip_code", source = "body.address.zipCode")
    @Mapping(target = "active", constant = "true")
    PatientEntity postBodyToEntity(SavePatientDTO body);

    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "modifiedAt", source = "modified_at")
    @Mapping(target = "address.zipCode", source = "entity.address.zip_code")
    SavePatientResponseDTO entityToPostResponseDTO(PatientEntity entity);

    GetPatientDTO entityToGetResponseDTO(PatientEntity entity);

    @Mapping(target = "address.zipCode", source = "entity.address.zip_code")
    UpdatePatientResponseDTO entityToPutResponseDTO(PatientEntity entity);

}
