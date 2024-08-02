package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.handler.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.mapper.PatientMapper;;
import com.hosp.med.voll.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
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

        return mapper.entityToPostResponseDTO(patient);
    }

    public Page<GetPatientDTO> listPatients(Pageable pagination) {

        Page<PatientEntity> patients = repository.findAllByActiveTrue(pagination);
        Page<GetPatientDTO> patientsPageDTO = patients.map(mapper::entityToGetResponseDTO);

        return patientsPageDTO;
    }

    public GetPatientDTO queryPatientById(Integer id) {

        var patient = repository.getReferenceById(id);
        return mapper.entityToGetResponseDTO(patient);
    }

    public UpdatePatientResponseDTO updatePatient(UpdatePatientRequestDTO body) throws UnactiveException {

        var patientRecord = repository.getReferenceById(body.getId());

        if (patientRecord.getActive() == false) {
            throw new UnactiveException();
        }

        if (body.getName() != null && !body.getName().equals(patientRecord.getName())) {
            patientRecord.setName(body.getName());
        }
        if (body.getPhone() != null && !body.getPhone().equals(patientRecord.getPhone())) {
            patientRecord.setPhone(body.getPhone());
        }
        if (body.getAddress() != null) {
            patientRecord.setAddress(addressMapper.addressDtoToModel(body.getAddress()));
        }

        repository.save(patientRecord);

        return mapper.entityToPutResponseDTO(patientRecord);
    }
    public void deletePatient(Integer id) {
        var patient = repository.getReferenceById(id);
        patient.setActive(false);
    }

}