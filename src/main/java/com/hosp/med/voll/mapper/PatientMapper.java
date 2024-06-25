package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.GetPatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientDTO;
import com.hosp.med.voll.domain.model.dto.SavePatientResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdatePatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface PatientMapper {

    PatientEntity postBodyToEntity(SavePatientDTO body);

    SavePatientResponseDTO entityToPostResponseDTO(PatientEntity entity);

    Page<GetPatientDTO> pageToResponseDTO(Page<PatientEntity> page, Pageable pagination);

    GetPatientDTO entityToGetResponseDTO(PatientEntity entity);

    UpdatePatientResponseDTO entityToPutResponseDTO(PatientEntity entity);

}
