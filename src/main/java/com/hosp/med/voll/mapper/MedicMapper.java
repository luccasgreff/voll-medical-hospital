package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.GetMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicDTO;
import com.hosp.med.voll.domain.model.dto.SaveMedicResponseDTO;
import com.hosp.med.voll.domain.model.dto.UpdateMedicResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MedicMapper {

    MedicEntity postBodyToEntity(SaveMedicDTO body);

    SaveMedicResponseDTO entityToPostResponseDTO(MedicEntity entity);

    Page<GetMedicDTO> pageToResponseDTO(Page<MedicEntity> page, Pageable pagination);

    GetMedicDTO entityToGetResponseDTO(MedicEntity entity);

    UpdateMedicResponseDTO entityToPutResponseDTO(MedicEntity entity);

}
