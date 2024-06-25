package com.hosp.med.voll.mapper.impl;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.GetAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentDTO;
import com.hosp.med.voll.domain.model.dto.SaveAppointmentResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateAppointmentResponseDTO;
import com.hosp.med.voll.mapper.AppointmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AppointmentMapperImpl implements AppointmentMapper {
    @Override
    public AppointmentEntity postBodyToEntity(SaveAppointmentDTO body) {

        AppointmentEntity entity = new AppointmentEntity();
        entity.setPatient(body.getPatient());
        entity.setMedic(body.getMedic());
        entity.setDate(body.getDate());
        entity.setTime(body.getTime());
        return entity;
    }

    @Override
    public SaveAppointmentResponseDTO entityToPostResponseDTO(AppointmentEntity entity) {

        SaveAppointmentResponseDTO responseDTO = new SaveAppointmentResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setPatient(entity.getPatient());
        responseDTO.setMedic(entity.getMedic());
        responseDTO.setDate(entity.getDate());
        responseDTO.setTime(entity.getTime());
        responseDTO.setCreatedAt(entity.getCreated_at());
        responseDTO.setModifiedAt(entity.getModified_at());
        return responseDTO;
    }

    @Override
    public Page<GetAppointmentDTO> pageToResponseDTO(Page<AppointmentEntity> page, Pageable pagination) {

        int offset = (int) pagination.getOffset();
        int end = Math.min((offset + pagination.getPageSize()), page.getSize());

        ArrayList<GetAppointmentDTO> listDTO = new ArrayList<GetAppointmentDTO>();

        for (var object : page.getContent()) {
            listDTO.add(GetAppointmentDTO.builder()
                    .id(object.getId())
                    .patient(object.getPatient())
                    .medic(object.getMedic())
                    .date(object.getDate())
                    .time(object.getTime()).build());
        }

        PageImpl<GetAppointmentDTO> responsePage = new PageImpl<GetAppointmentDTO>(listDTO, pagination, end);

        return responsePage;
    }

    @Override
    public GetAppointmentDTO entityToGetResponseDTO(AppointmentEntity entity) {

        var responseDTO  = new GetAppointmentDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setPatient(entity.getPatient());
        responseDTO.setMedic(entity.getMedic());
        responseDTO.setDate(entity.getDate());
        responseDTO.setTime(entity.getTime());

        return responseDTO;
    }

    @Override
    public UpdateAppointmentResponseDTO entityToPutResponseDTO(AppointmentEntity entity) {

        var responseDTO = new UpdateAppointmentResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setDate(entity.getDate());
        responseDTO.setTime(entity.getTime());

        return responseDTO;
    }

}
