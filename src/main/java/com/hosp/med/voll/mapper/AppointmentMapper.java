package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.appointment.GetAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.appointment.SaveAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.appointment.SaveAppointmentResponseDTO;
import com.hosp.med.voll.domain.model.dto.appointment.UpdateAppointmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {


    AppointmentEntity postBodyToEntity(SaveAppointmentDTO body);

    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "modifiedAt", source = "modified_at")
    SaveAppointmentResponseDTO entityToPostResponseDTO(AppointmentEntity entity);

    GetAppointmentDTO entityToGetResponseDTO(AppointmentEntity entity);

    UpdateAppointmentResponseDTO entityToPutResponseDTO(AppointmentEntity entity);

}
