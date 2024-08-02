package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.GetAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateAppointmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {


    AppointmentEntity postBodyToEntity(SaveAppointmentDTO body);

    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "modifiedAt", source = "modified_at")
    SaveAppointmentResponseDTO entityToPostResponseDTO(AppointmentEntity entity);

    GetAppointmentDTO entityToGetResponseDTO(AppointmentEntity entity);

    UpdateAppointmentResponseDTO entityToPutResponseDTO(AppointmentEntity entity);

}
