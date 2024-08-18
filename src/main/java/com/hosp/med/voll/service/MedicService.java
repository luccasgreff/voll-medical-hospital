package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.handler.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.mapper.MedicMapper;
import com.hosp.med.voll.repository.MedicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MedicService {

    @Autowired
    private MedicRepository repository;
    @Autowired
    private MedicMapper mapper;
    @Autowired
    private AddressMapper addressMapper;


    public SaveMedicResponseDTO saveMedic(SaveMedicDTO body) {

        var medic = mapper.postRequestDTOToEntity(body);

        repository.save(medic);

        log.info("Medic registered: {name: {}, email: {}, specialty: {}}", medic.getName(), medic.getEmail(), medic.getSpecialty());

        return mapper.entityToPostResponseDTO(medic);
    }

    public Page<GetMedicDTO> listMedics(Pageable pagination) {

        Page<MedicEntity> medics = repository.findAllByActiveTrue(pagination);
        Page<GetMedicDTO> medicsPageDTO = medics.map(mapper::entityToGetResponseDTO);

        log.info("Listing active medics following: {offset: {}, size: {}, page number: {}}", pagination.getOffset(), pagination.getPageSize(), pagination.getPageNumber());

        return medicsPageDTO;
    }

    public GetMedicDTO queryMedicById(Integer id) {

        var medic = repository.getReferenceById(id);

        log.info("Showing medic record: {name: {}, email: {}, specialty: {}}", medic.getName(), medic.getEmail(), medic.getSpecialty());

        return mapper.entityToGetResponseDTO(medic);
    }

    public UpdateMedicResponseDTO updateMedic(UpdateMedicRequestDTO body) throws UnactiveException {

        var medicRecord = repository.getReferenceById(body.getId());

        if (medicRecord.getActive() == false) {
            log.error("Unable to update medic record, because It's unactive");
            throw new UnactiveException();
        }

        String logForUpdatedData = "Medic data updated: {";

        if (body.getName() != null && !body.getName().equals(medicRecord.getName())) {
            medicRecord.setName(body.getName());
            logForUpdatedData += "name: " + body.getName();
        }
        if (body.getPhone() != null && !body.getPhone().equals(medicRecord.getPhone())) {
            medicRecord.setPhone(body.getPhone());
            logForUpdatedData += ", phone: " + body.getPhone();
        }
        if (body.getAddress() != null) {
            medicRecord.setAddress(addressMapper.addressDtoToModel(body.getAddress()));
            logForUpdatedData += ", address: ****}";
        }

        repository.save(medicRecord);

        log.info(logForUpdatedData);

        return mapper.entityToPutResponseDTO(medicRecord);
    }

    public void deleteMedic(Integer id) {
        var medic = repository.getReferenceById(id);
        medic.setActive(false);

        log.info("Medic record with id {} set as unactive", id);
    }

}