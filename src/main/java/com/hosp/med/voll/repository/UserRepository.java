package com.hosp.med.voll.repository;

import com.hosp.med.voll.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserDetails findByLogin(String login);
}
