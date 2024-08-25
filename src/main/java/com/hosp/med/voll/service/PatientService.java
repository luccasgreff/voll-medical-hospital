package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.patient.*;
import com.hosp.med.voll.domain.model.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.mapper.PatientMapper;
import com.hosp.med.voll.repository.PatientRepository;
import com.hosp.med.voll.util.LogUtils;
import com.hosp.med.voll.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private PatientMapper mapper;

    @Autowired
    private AddressMapper addressMapper;

    public SavePatientResponseDTO savePatient(SavePatientDTO body) {

        var patient = mapper.postBodyToEntity(body);

        repository.save(patient);

        log.info("Patient registered: {name: {}, email: {}, phone: {}}", patient.getName(), patient.getEmail(), patient.getPhone());

        return mapper.entityToPostResponseDTO(patient);
    }

    public Page<GetPatientDTO> listPatients(Pageable pagination) {

        Page<PatientEntity> patients = repository.findAllByActiveTrue(pagination);
        Page<GetPatientDTO> patientsPageDTO = patients.map(mapper::entityToGetResponseDTO);

        log.info("Listing active patients following: {offset: {}, size: {}, page number: {}}", pagination.getOffset(), pagination.getPageSize(), pagination.getPageNumber());

        return patientsPageDTO;
    }

    public GetPatientDTO queryPatientById(Integer id) {

        var patient = repository.getReferenceById(id);

        log.info("Showing patient record: {name: {}, email: {}, phone: {}}", patient.getName(), patient.getEmail(), patient.getPhone());

        return mapper.entityToGetResponseDTO(patient);
    }

    public UpdatePatientResponseDTO updatePatient(UpdatePatientRequestDTO body) throws UnactiveException {

        var patientRecord = repository.getReferenceById(body.getId());

        ServiceUtils.isUnactive(patientRecord.getActive());

        String updatedDataLog = LogUtils.buildPatientUpdatedDataLog(body, patientRecord);

        if (!StringUtils.isBlank(body.getName()) && !body.getName().equals(patientRecord.getName())) {
            patientRecord.setName(body.getName());
        }

        if (!StringUtils.isBlank(body.getPhone()) && !body.getPhone().equals(patientRecord.getPhone())) {
            patientRecord.setPhone(body.getPhone());
        }

        if (body.getAddress() != null) {
            patientRecord.setAddress(addressMapper.addressDtoToModel(body.getAddress()));
        }

        log.info(updatedDataLog);

        repository.save(patientRecord);

        return mapper.entityToPutResponseDTO(patientRecord);
    }

    public void deletePatient(Integer id) {
        var patient = repository.getReferenceById(id);
        patient.setActive(false);

        log.info("Patient record with id {} set as unactive", id);
    }

}