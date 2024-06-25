package com.hosp.med.voll.repository;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository <AppointmentEntity, Integer> {

}
