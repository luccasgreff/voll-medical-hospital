package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.domain.model.exception.UnactiveException;
import com.hosp.med.voll.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {


    @Autowired
    private PatientService service;

    @PostMapping
    @Transactional
    public ResponseEntity<SavePatientResponseDTO> savePatient(@RequestBody @Valid SavePatientDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.savePatient(body));
    }

    @GetMapping
    public Page<GetPatientDTO> listPatients(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        return service.listPatients(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPatientDTO> queryPatientById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.queryPatientById(id));
    }

    @PutMapping
    public ResponseEntity<UpdatePatientResponseDTO> updatePatient(@RequestBody @Valid UpdatePatientRequestDTO body) throws UnactiveException {
        return ResponseEntity.ok(service.updatePatient(body));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePatient(@PathVariable Integer id) {
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
