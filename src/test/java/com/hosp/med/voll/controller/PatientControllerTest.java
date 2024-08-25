package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.address.SaveAddressDTO;
import com.hosp.med.voll.domain.model.dto.address.SaveAddressResponseDTO;
import com.hosp.med.voll.domain.model.dto.patient.*;
import com.hosp.med.voll.domain.model.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.repository.PatientRepository;
import com.hosp.med.voll.service.PatientService;
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
class PatientControllerTest {

    @Autowired
    private MockMvc controllerMock;

    @MockBean
    private PatientService serviceMock;

    @Mock
    private PatientRepository repository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private JacksonTester<SavePatientDTO> patientJsonRequest;

    @Autowired
    private JacksonTester<SavePatientResponseDTO> patientJsonResponse;

    @Autowired
    private JacksonTester<PageImpl<GetPatientDTO>> jsonPatientList;

    @Autowired
    private JacksonTester<GetPatientDTO> jsonPatientId;

    @Autowired
    private JacksonTester<UpdatePatientRequestDTO> jsonPatientUpdateRequest;

    @Autowired
    private JacksonTester<UpdatePatientResponseDTO> jsonPatientUpdateResponse;

    private String controllerEndpoint = "/patients";

    @Test
    @WithMockUser
    public void savePatient() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("27");
        addressRequest.setComplement("Complement Test");

        var request = new SavePatientDTO();
        request.setName("test");
        request.setEmail("test@test.com");
        request.setPhone("11990023201");
        request.setSsn("400.222.375/33");
        request.setAddress(addressRequest);

        var addressResponse = new SaveAddressResponseDTO();
        addressResponse.setStreet(addressRequest.getStreet());
        addressResponse.setDistrict(addressRequest.getDistrict());
        addressResponse.setCity(addressRequest.getCity());
        addressResponse.setZipCode(addressRequest.getZipCode());
        addressResponse.setState(addressRequest.getState());
        addressResponse.setNumber(addressRequest.getNumber());
        addressResponse.setComplement(addressRequest.getComplement());

        var awaitedResponse = new SavePatientResponseDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setName(request.getName());
        awaitedResponse.setEmail(request.getEmail());
        awaitedResponse.setSsn(request.getSsn());
        awaitedResponse.setPhone(request.getPhone());
        awaitedResponse.setAddress(addressResponse);
        awaitedResponse.setActive(true);
        awaitedResponse.setCreatedAt(LocalDateTime.now());
        awaitedResponse.setModifiedAt(LocalDateTime.now());

        when(serviceMock.savePatient(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJsonRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = patientJsonResponse.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void savePatientNull() throws Exception {
        var response = controllerMock.perform(post(controllerEndpoint)).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    public void listPatients() throws Exception {

        var firstMedic = new GetPatientDTO();
        firstMedic.setId(1);
        firstMedic.setName("patient 1");
        firstMedic.setEmail("patient1@test.com");
        firstMedic.setSsn("100.000.000/00");

        var secondMedic = new GetPatientDTO();
        secondMedic.setId(2);
        secondMedic.setName("patient 2");
        secondMedic.setEmail("patient2@test.com");
        secondMedic.setSsn("200.000.000/00");

        ArrayList<GetPatientDTO> list = new ArrayList<GetPatientDTO>();
        list.add(firstMedic);
        list.add(secondMedic);

        PageRequest paginacao = PageRequest.of(1, 1);

        var awaitedResponse = new PageImpl<GetPatientDTO>(list, paginacao, 1);

        when(serviceMock.listPatients(any())).thenReturn(awaitedResponse);
        var response = controllerMock.perform(get(controllerEndpoint)).andReturn().getResponse();

        var awaitedJson = jsonPatientList.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void patientById() throws Exception {

        var awaitedResponse = new GetPatientDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setName("patient test");
        awaitedResponse.setEmail("patient@test.com");
        awaitedResponse.setSsn("400.222.375/33");

        when(serviceMock.queryPatientById(1)).thenReturn(awaitedResponse);
        var response = controllerMock.perform(get(controllerEndpoint + "/1")).andReturn().getResponse();

        var awaitedJson = jsonPatientId.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void updatePatient() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("88");
        addressRequest.setComplement("Complement Test");

        var request = new UpdatePatientRequestDTO();
        request.setId(1);
        request.setName("update patient");
        request.setPhone("22990073209");
        request.setAddress(addressRequest);

        var addressResponse = new SaveAddressResponseDTO();
        addressResponse.setStreet(addressRequest.getStreet());
        addressResponse.setDistrict(addressRequest.getDistrict());
        addressResponse.setCity(addressRequest.getCity());
        addressResponse.setZipCode(addressRequest.getZipCode());
        addressResponse.setState(addressRequest.getState());
        addressResponse.setNumber(addressRequest.getNumber());
        addressResponse.setComplement(addressRequest.getComplement());

        var awaitedResponse = new UpdatePatientResponseDTO();
        awaitedResponse.setId(request.getId());
        awaitedResponse.setName(request.getName());
        awaitedResponse.setPhone(request.getPhone());
        awaitedResponse.setAddress(addressResponse);

        when(serviceMock.updatePatient(request)).thenReturn(awaitedResponse);
        var response = controllerMock.perform(put(controllerEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPatientUpdateRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonPatientUpdateResponse.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void updateUnactivePatient() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("88");
        addressRequest.setComplement("Complement Test");

        var request = new UpdatePatientRequestDTO();
        request.setId(1);
        request.setName("update patient");
        request.setPhone("22990073209");
        request.setAddress(addressRequest);

        var addressResponse = addressMapper.addressDtoToModel(addressRequest);

        var entity = new PatientEntity();
        entity.setId(1);
        entity.setName("test");
        entity.setPhone("11990023201");
        entity.setEmail("update@test.com.br");
        entity.setSsn("400.222.375/33");
        entity.setAddress(addressResponse);
        entity.setActive(false);
        entity.setCreated_at(LocalDateTime.now());
        entity.setModified_at(LocalDateTime.now());

        repository.save(entity);

        when(serviceMock.updatePatient(request)).thenThrow(UnactiveException.class);

        var mockRequest = controllerMock.perform(put(controllerEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPatientUpdateRequest.write(request).getJson()))
                .andReturn();

        var status = mockRequest.getResponse().getStatus();
        var exceptionCaught = mockRequest.getResolvedException().getClass();


        assertEquals(status, HttpStatus.BAD_REQUEST.value());
        assertEquals(exceptionCaught, UnactiveException.class);
    }


    @Test
    @WithMockUser
    public void deletePatient() throws Exception {
        var response = controllerMock.perform(delete(controllerEndpoint + "/1")).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }
}