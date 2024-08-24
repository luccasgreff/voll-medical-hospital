package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.medic.GetMedicDTO;
import com.hosp.med.voll.domain.model.dto.medic.SaveMedicDTO;
import com.hosp.med.voll.domain.model.dto.medic.SaveMedicResponseDTO;
import com.hosp.med.voll.domain.model.dto.medic.UpdateMedicResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
