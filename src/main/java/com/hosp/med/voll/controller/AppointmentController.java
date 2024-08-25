package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.dto.appointment.*;
import com.hosp.med.voll.service.AppointmentService;
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
@RequestMapping("/appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    @Transactional
    public ResponseEntity<SaveAppointmentResponseDTO> saveAppointment(@RequestBody @Valid SaveAppointmentDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.saveAppointment(body));
    }

    @GetMapping
    public Page<GetAppointmentDTO> listAppointments(@PageableDefault(size = 5) Pageable pagination) {
        return service.listAppointments(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAppointmentDTO> queryAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.queryAppointmentById(id));
    }

    @PutMapping
    public ResponseEntity<UpdateAppointmentResponseDTO> updateAppointment(@RequestBody @Valid UpdateAppointmentRequestDTO body) {
        return ResponseEntity.ok(service.updateAppointment(body));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAppointment(@PathVariable Integer id) {

        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
