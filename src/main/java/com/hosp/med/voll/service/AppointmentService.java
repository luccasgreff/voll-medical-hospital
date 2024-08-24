package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.dto.appointment.*;
import com.hosp.med.voll.mapper.AppointmentMapper;
import com.hosp.med.voll.repository.AppointmentRepository;
import com.hosp.med.voll.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private AppointmentMapper mapper;

    public SaveAppointmentResponseDTO saveAppointment(SaveAppointmentDTO body) {

        var appointment = mapper.postBodyToEntity(body);

        repository.save(appointment);

        log.info("Appointment booked: {patient: {}, medic: {}, date: {}, time: {}}", appointment.getPatient(), appointment.getMedic(), appointment.getDate(), appointment.getTime());

        return mapper.entityToPostResponseDTO(appointment);
    }

    public Page<GetAppointmentDTO> listAppointments(Pageable pagination) {

        Page<AppointmentEntity> appointments = repository.findAll(pagination);
        Page<GetAppointmentDTO> appointmentsPageDTO = appointments.map(mapper::entityToGetResponseDTO);

        log.info("Listing appointments following: {offset: {}, size: {}, page number: {}}", pagination.getOffset(), pagination.getPageSize(), pagination.getPageNumber());

        return appointmentsPageDTO;
    }

    public GetAppointmentDTO queryAppointmentById(Integer id) {

        var appointment = repository.getReferenceById(id);

        log.info("Showing appointment: {patient: {}, medic: {}, date: {}, time: {}}", appointment.getPatient(), appointment.getMedic(), appointment.getDate(), appointment.getTime());

        return mapper.entityToGetResponseDTO(appointment);
    }

    public UpdateAppointmentResponseDTO updateAppointment(UpdateAppointmentRequestDTO body) {

        var appointmentRecord = repository.getReferenceById(body.getId());

        String updatedDataLog = LogUtils.buildAppointmentUpdatedDataLog(body, appointmentRecord);

        if (!StringUtils.isBlank(body.getDate()) && !body.getDate().equals(appointmentRecord.getDate())) {
            appointmentRecord.setDate(body.getDate());
        }

        if (!StringUtils.isBlank(body.getTime()) && !body.getTime().equals(appointmentRecord.getTime())) {
            appointmentRecord.setTime(body.getTime());
        }

        log.info(updatedDataLog);

        repository.save(appointmentRecord);

        return mapper.entityToPutResponseDTO(appointmentRecord);
    }

    public void deleteAppointment(Integer id) {

        repository.deleteById(id);

        log.info("Deleted appointment with id {}", id);
    }


}