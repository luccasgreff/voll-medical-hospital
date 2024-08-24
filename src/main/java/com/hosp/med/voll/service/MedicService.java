package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.medic.*;
import com.hosp.med.voll.domain.model.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.mapper.MedicMapper;
import com.hosp.med.voll.repository.MedicRepository;
import com.hosp.med.voll.util.LogUtils;
import com.hosp.med.voll.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

        ServiceUtils.isUnactive(medicRecord.getActive());

        String updatedDataLog = LogUtils.buildMedicUpdatedDataLog(body, medicRecord);

        if (!StringUtils.isBlank(body.getName()) && !body.getName().equals(medicRecord.getName())) {
            medicRecord.setName(body.getName());
        }

        if (!StringUtils.isBlank(body.getPhone()) && !body.getPhone().equals(medicRecord.getPhone())) {
            medicRecord.setPhone(body.getPhone());
        }

        if (body.getAddress() != null) {
            medicRecord.setAddress(addressMapper.addressDtoToModel(body.getAddress()));
        }

        log.info(updatedDataLog);

        repository.save(medicRecord);

        return mapper.entityToPutResponseDTO(medicRecord);
    }

    public void deleteMedic(Integer id) {
        var medic = repository.getReferenceById(id);
        medic.setActive(false);

        log.info("Medic record with id {} set as unactive", id);
    }

}