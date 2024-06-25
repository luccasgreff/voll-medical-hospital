package com.hosp.med.voll.mapper.impl;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.GetPatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdatePatientResponseDTO;
import com.hosp.med.voll.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PatientMapperImpl implements PatientMapper {
    @Autowired
    private AddressMapperImpl addressMapper;

    @Override
    public PatientEntity postBodyToEntity(SavePatientDTO body) {

        var address = addressMapper.addressDtoToModel(body.getAddress());

        PatientEntity entity = new PatientEntity();
        entity.setActive(true);
        entity.setName(body.getName());
        entity.setEmail(body.getEmail());
        entity.setPhone(body.getPhone());
        entity.setSsn(body.getSsn());
        entity.setAddress(address);
        return entity;

    }

    @Override
    public SavePatientResponseDTO entityToPostResponseDTO(PatientEntity entity) {

        var addressResponse = addressMapper.addressModelToResponseDTO(entity.getAddress());

        SavePatientResponseDTO responseDTO = new SavePatientResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setSsn(entity.getSsn());
        responseDTO.setAddress(addressResponse);
        responseDTO.setActive(entity.getActive());
        responseDTO.setCreatedAt(entity.getCreated_at());
        responseDTO.setModifiedAt(entity.getModified_at());
        return responseDTO;
    }

    @Override
    public Page<GetPatientDTO> pageToResponseDTO(Page<PatientEntity> page, Pageable pagination) {

        int offset = (int) pagination.getOffset();
        int end = Math.min((offset + pagination.getPageSize()), page.getSize());

        ArrayList<GetPatientDTO> listDTO = new ArrayList<GetPatientDTO>();

        for (var patient : page) {
            listDTO.add(GetPatientDTO.builder()
                    .id(patient.getId())
                    .name(patient.getName())
                    .email(patient.getEmail())
                    .ssn(patient.getSsn()).build());
        }

        PageImpl<GetPatientDTO> responsePage = new PageImpl<GetPatientDTO>(listDTO, pagination, end);

        return responsePage;
    }

    @Override
    public GetPatientDTO entityToGetResponseDTO(PatientEntity entity) {

        var responseDTO  = new GetPatientDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setSsn(entity.getSsn());

        return responseDTO;
    }

    @Override
    public UpdatePatientResponseDTO entityToPutResponseDTO(PatientEntity entity) {

        var responseDTO = new UpdatePatientResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setAddress(addressMapper.addressModelToResponseDTO(entity.getAddress()));
        return responseDTO;
    }

}
