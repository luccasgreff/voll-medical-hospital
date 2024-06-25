package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.repository.AppointmentRepository;
import com.hosp.med.voll.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc controllerMock;

    @MockBean
    private AppointmentService serviceMock;

    @Mock
    private AppointmentRepository repository;

    @Autowired
    private JacksonTester<SaveAppointmentDTO> jsonAppointmentRequest;

    @Autowired
    private JacksonTester<SaveAppointmentResponseDTO> jsonAppointmentResponse;

    @Autowired
    private JacksonTester<PageImpl<GetAppointmentDTO>> jsonAppointmentList;

    @Autowired
    private JacksonTester<GetAppointmentDTO> jsonAppointmentId;

    @Autowired
    private JacksonTester<UpdateAppointmentRequestDTO> jsonAppointmentUpdateRequest;

    @Autowired
    private JacksonTester<UpdateAppointmentResponseDTO> jsonAppointmentUpdateResponse;

    private String controllerEndpoint = "/appointments";

    @Test
    @WithMockUser
    public void saveAppointment() throws Exception {

        var request = new SaveAppointmentDTO();
        request.setMedic("medic");
        request.setPatient("patient");
        request.setDate("20/10/2024");
        request.setTime("19:00");

        var awaitedResponse = new SaveAppointmentResponseDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setMedic("test");
        awaitedResponse.setPatient("patient");
        awaitedResponse.setDate("20/10/2024");
        awaitedResponse.setTime("19:00");
        awaitedResponse.setCreatedAt(LocalDateTime.now());
        awaitedResponse.setModifiedAt(LocalDateTime.now());

        when(serviceMock.saveAppointment(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAppointmentRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonAppointmentResponse.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void saveAppointmentNull() throws Exception {
        var response = controllerMock.perform(post(controllerEndpoint)).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    public void appointmentsList() throws Exception {

        var firstAppointment = new GetAppointmentDTO();
        firstAppointment.setId(1);
        firstAppointment.setMedic("test");
        firstAppointment.setPatient("patient");
        firstAppointment.setDate("20/10/2024");
        firstAppointment.setTime("19:00");

        var secondAppointment = new GetAppointmentDTO();
        secondAppointment.setId(2);
        secondAppointment.setMedic("test 2");
        secondAppointment.setPatient("patient 2");
        secondAppointment.setDate("20/11/2024");
        secondAppointment.setTime("19:00");

        ArrayList<GetAppointmentDTO> list = new ArrayList<GetAppointmentDTO>();
        list.add(firstAppointment);
        list.add(secondAppointment);

        PageRequest pageable = PageRequest.of(1, 1);

        var awaitedResponse = new PageImpl<GetAppointmentDTO>(list, pageable, 1);

        when(serviceMock.listAppointments(any())).thenReturn(awaitedResponse);

        var response = controllerMock.perform(get(controllerEndpoint)).andReturn().getResponse();

        var awaitedJson = jsonAppointmentList.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void appointmentById() throws Exception {

        var awaitedResponse = new GetAppointmentDTO();
        awaitedResponse.setMedic("test");
        awaitedResponse.setPatient("patient");
        awaitedResponse.setDate("21/11/2024");
        awaitedResponse.setTime("17:00");

        when(serviceMock.queryAppointmentById(1)).thenReturn(awaitedResponse);
        var response = controllerMock.perform(get(controllerEndpoint + "/1")).andReturn().getResponse();

        var awaitedJson = jsonAppointmentId.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void updateAppointment() throws Exception {

        var request = new UpdateAppointmentRequestDTO();
        request.setId(1);
        request.setDate("20/10/2025");
        request.setTime("18:00");

        var awaitedResponse = new UpdateAppointmentResponseDTO();
        awaitedResponse.setId(request.getId());
        awaitedResponse.setDate(request.getDate());
        awaitedResponse.setTime(request.getTime());

        when(serviceMock.updateAppointment(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(put(controllerEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAppointmentUpdateRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonAppointmentUpdateResponse.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void deleteAppointment() throws Exception {
        var response = controllerMock.perform(delete(controllerEndpoint + "/1")).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }
}