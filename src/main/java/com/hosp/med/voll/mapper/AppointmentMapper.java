package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.GetAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateAppointmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface AppointmentMapper {

    AppointmentEntity postBodyToEntity(SaveAppointmentDTO body);

    SaveAppointmentResponseDTO entityToPostResponseDTO(AppointmentEntity entity);

    Page<GetAppointmentDTO> pageToResponseDTO(Page<AppointmentEntity> page, Pageable pagination);

    GetAppointmentDTO entityToGetResponseDTO(AppointmentEntity entity);

    UpdateAppointmentResponseDTO entityToPutResponseDTO(AppointmentEntity entity);

}
