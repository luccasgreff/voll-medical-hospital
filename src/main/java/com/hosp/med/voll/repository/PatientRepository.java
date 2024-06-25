package com.hosp.med.voll.repository;

import com.hosp.med.voll.domain.model.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository <PatientEntity, Integer> {
    Page<PatientEntity> findAllByActiveTrue(Pageable pagination);
}
