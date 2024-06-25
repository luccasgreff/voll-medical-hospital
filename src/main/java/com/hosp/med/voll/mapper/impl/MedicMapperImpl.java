package com.hosp.med.voll.mapper.impl;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.GetMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateMedicResponseDTO;
import com.hosp.med.voll.mapper.MedicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MedicMapperImpl implements MedicMapper {

    @Autowired
    private AddressMapperImpl addressMapper;

    @Override
    public MedicEntity postBodyToEntity(SaveMedicDTO body) {

        var address = addressMapper.addressDtoToModel(body.getAddress());

        MedicEntity entity = new MedicEntity();
        entity.setName(body.getName());
        entity.setEmail(body.getEmail());
        entity.setPhone(body.getPhone());
        entity.setCrm(body.getCrm());
        entity.setSpecialty(body.getSpecialty());
        entity.setAddress(address);
        entity.setActive(true);
        return entity;

    }

    @Override
    public SaveMedicResponseDTO entityToPostResponseDTO(MedicEntity entity) {

        var addressResponse = addressMapper.addressModelToResponseDTO(entity.getAddress());

        SaveMedicResponseDTO responseDTO = new SaveMedicResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setCrm(entity.getCrm());
        responseDTO.setSpecialty(entity.getSpecialty());
        responseDTO.setAddress(addressResponse);
        responseDTO.setActive(entity.getActive());
        responseDTO.setCreatedAt(entity.getCreated_at());
        responseDTO.setModifiedAt(entity.getModified_at());
        return responseDTO;
    }

    @Override
    public Page<GetMedicDTO> pageToResponseDTO(Page<MedicEntity> page, Pageable pagination) {

        int offset = (int) pagination.getOffset();
        int end = Math.min((offset + pagination.getPageSize()), page.getSize());

        ArrayList<GetMedicDTO> listDTO = new ArrayList<GetMedicDTO>();

        for (var object : page.getContent()) {
            listDTO.add(GetMedicDTO.builder()
                    .id(object.getId())
                    .name(object.getName())
                    .email(object.getEmail())
                    .crm(object.getCrm())
                    .specialty(object.getSpecialty()).build());
        }

        PageImpl<GetMedicDTO> responsePage = new PageImpl<GetMedicDTO>(listDTO, pagination, end);

        return responsePage;
    }

    @Override
    public GetMedicDTO entityToGetResponseDTO(MedicEntity entity) {

        var responseDTO  = new GetMedicDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setCrm(entity.getCrm());
        responseDTO.setSpecialty(entity.getSpecialty());

        return responseDTO;
    }

    @Override
    public UpdateMedicResponseDTO entityToPutResponseDTO(MedicEntity entity) {

        var responseDTO = new UpdateMedicResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setAddress(addressMapper.addressModelToResponseDTO(entity.getAddress()));
        return responseDTO;
    }

}
