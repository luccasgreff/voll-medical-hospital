package com.hosp.med.voll.service;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.handler.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.mapper.MedicMapper;
import com.hosp.med.voll.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
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

        return mapper.entityToPostResponseDTO(medic);
    }

    public Page<GetMedicDTO> listMedics(Pageable pagination) {

        Page<MedicEntity> medics = repository.findAllByActiveTrue(pagination);
        Page<GetMedicDTO> medicsPageDTO = medics.map(mapper::entityToGetResponseDTO);

        return medicsPageDTO;
    }

    public GetMedicDTO queryMedicById(Integer id) {

        var medic = repository.getReferenceById(id);
        return mapper.entityToGetResponseDTO(medic);
    }

    public UpdateMedicResponseDTO updateMedic(UpdateMedicRequestDTO body) throws UnactiveException {

        var medicRecord = repository.getReferenceById(body.getId());

        if (medicRecord.getActive() == false) {
            throw new UnactiveException();
        }

        if (body.getName() != null && !body.getName().equals(medicRecord.getName())) {
            medicRecord.setName(body.getName());
        }
        if (body.getPhone() != null && !body.getPhone().equals(medicRecord.getPhone())) {
            medicRecord.setPhone(body.getPhone());
        }
        if (body.getAddress() != null) {
            medicRecord.setAddress(addressMapper.addressDtoToModel(body.getAddress()));
        }

        repository.save(medicRecord);

        return mapper.entityToPutResponseDTO(medicRecord);
    }

    public void deleteMedic(Integer id) {
        var medic = repository.getReferenceById(id);
        medic.setActive(false);
    }

}