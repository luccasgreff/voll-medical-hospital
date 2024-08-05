package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.GetMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateMedicResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface MedicMapper {

    MedicMapper INSTANCE = Mappers.getMapper(MedicMapper.class);


    @Mapping(target = "address.zip_code", source = "body.address.zipCode")
    @Mapping(target = "active", constant = "true")
    MedicEntity postRequestDTOToEntity(SaveMedicDTO body);

    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "modifiedAt", source = "modified_at")
    @Mapping(target = "address.zipCode", source = "entity.address.zip_code")
    SaveMedicResponseDTO entityToPostResponseDTO(MedicEntity entity);

    GetMedicDTO entityToGetResponseDTO(MedicEntity entity);

    @Mapping(target = "address.zipCode", source = "entity.address.zip_code")
    UpdateMedicResponseDTO entityToPutResponseDTO(MedicEntity entity);

}
