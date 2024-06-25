package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.mapper.impl.AppointmentMapperImpl;
import com.hosp.med.voll.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private AppointmentMapperImpl mapper;

    public SaveAppointmentResponseDTO saveAppointment(SaveAppointmentDTO body) {

        var appointment = mapper.postBodyToEntity(body);

        repository.save(appointment);

        return mapper.entityToPostResponseDTO(appointment);
    }

    public Page<GetAppointmentDTO> listAppointments(Pageable pagination) {

        Page<AppointmentEntity> appointments = repository.findAll(pagination);
        return mapper.pageToResponseDTO(appointments, pagination);
    }

    public GetAppointmentDTO queryAppointmentById(Integer id) {

        var appointment = repository.getReferenceById(id);
        return mapper.entityToGetResponseDTO(appointment);
    }

    public UpdateAppointmentResponseDTO updateAppointment(UpdateAppointmentRequestDTO body) {

        var appointmentRecord = repository.getReferenceById(body.getId());

        if (body.getDate() != null && !body.getDate().equals(appointmentRecord.getDate())) {
            appointmentRecord.setDate(body.getDate());
        }
        if (body.getTime() != null && !body.getTime().equals(appointmentRecord.getTime())) {
            appointmentRecord.setTime(body.getTime());
        }

        repository.save(appointmentRecord);

        return mapper.entityToPutResponseDTO(appointmentRecord);
    }

    public void deleteAppointment(Integer id) {
        repository.deleteById(id);
    }


}