package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.handler.exception.UnactiveException;
import com.hosp.med.voll.service.MedicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medics")
@SecurityRequirement(name = "bearer-key")
public class MedicController {

    @Autowired
    private MedicService service;

    @PostMapping
    @Transactional
    public ResponseEntity<SaveMedicResponseDTO> saveMedic(@RequestBody @Valid SaveMedicDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.saveMedic(body));
    }
    @GetMapping
    public Page<GetMedicDTO> listMedic(Pageable pagination) {
        return service.listMedics(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMedicDTO> queryMedicById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.queryMedicById(id));
    }

    @PutMapping
    public ResponseEntity<UpdateMedicResponseDTO> updateMedic(@RequestBody @Valid UpdateMedicRequestDTO body) throws UnactiveException {
        return ResponseEntity.ok(service.updateMedic(body));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedic(@PathVariable Integer id) {
        service.deleteMedic(id);
        return ResponseEntity.noContent().build();
    }

}
