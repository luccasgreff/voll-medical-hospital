package com.hosp.med.voll.controller;

import com.hosp.med.voll.domain.enums.SpecialtysEnum;
import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.dto.*;
import com.hosp.med.voll.handler.exception.UnactiveException;
import com.hosp.med.voll.mapper.AddressMapper;
import com.hosp.med.voll.repository.MedicRepository;
import com.hosp.med.voll.service.MedicService;
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
class MedicControllerTest {

    @Autowired
    private MockMvc controllerMock;

    @MockBean
    private MedicService serviceMock;

    @Mock
    private MedicRepository repository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private JacksonTester<SaveMedicDTO> medicJsonRequest;

    @Autowired
    private JacksonTester<SaveMedicResponseDTO> medicJsonResponse;

    @Autowired
    private JacksonTester<PageImpl<GetMedicDTO>> jsonMedicList;

    @Autowired
    private JacksonTester<GetMedicDTO> jsonMedicId;

    @Autowired
    private JacksonTester<UpdateMedicRequestDTO> jsonMedicUpdateRequest;

    @Autowired
    private JacksonTester<UpdateMedicResponseDTO> jsonMedicUpdateResponse;

    private String controllerEndpoint = "/medics";

    @Test
    @WithMockUser
    public void saveMedic() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("27");
        addressRequest.setComplement("Complement Test");

        var request = new SaveMedicDTO();
        request.setName("test");
        request.setEmail("test@test.com");
        request.setPhone("11990023201");
        request.setCrm("654325");
        request.setSpecialty(SpecialtysEnum.CARDIOLOGY);
        request.setAddress(addressRequest);

        var addressResponse = new SaveAddressResponseDTO();
        addressResponse.setStreet(addressRequest.getStreet());
        addressResponse.setDistrict(addressRequest.getDistrict());
        addressResponse.setCity(addressRequest.getCity());
        addressResponse.setZipCode(addressRequest.getZipCode());
        addressResponse.setState(addressRequest.getState());
        addressResponse.setNumber(addressRequest.getNumber());
        addressResponse.setComplement(addressRequest.getComplement());

        var awaitedResponse = new SaveMedicResponseDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setName(request.getName());
        awaitedResponse.setEmail(request.getEmail());
        awaitedResponse.setPhone(request.getPhone());
        awaitedResponse.setCrm(request.getCrm());
        awaitedResponse.setSpecialty(request.getSpecialty());
        awaitedResponse.setAddress(addressResponse);
        awaitedResponse.setActive(true);
        awaitedResponse.setCreatedAt(LocalDateTime.now());
        awaitedResponse.setModifiedAt(LocalDateTime.now());

        when(serviceMock.saveMedic(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicJsonRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = medicJsonResponse.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void saveMedicNull() throws Exception {
        var response = controllerMock.perform(post(controllerEndpoint)).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    public void listMedics() throws Exception {

        var firstMedic = new GetMedicDTO();
        firstMedic.setId(1);
        firstMedic.setName("medic 1");
        firstMedic.setEmail("medic1@test.com");
        firstMedic.setCrm("123456");
        firstMedic.setSpecialty(SpecialtysEnum.ORTHOPEDICS);


        var secondMedic = new GetMedicDTO();
        secondMedic.setId(2);
        secondMedic.setName("medic 2");
        secondMedic.setEmail("medic2@test.com");
        secondMedic.setCrm("654326");
        secondMedic.setSpecialty(SpecialtysEnum.DERMATOLOGY);

        ArrayList<GetMedicDTO> list = new ArrayList<GetMedicDTO>();
        list.add(firstMedic);
        list.add(secondMedic);

        PageRequest paginacao = PageRequest.of(1, 1);

        var awaitedResponse = new PageImpl<GetMedicDTO>(list, paginacao, 1);

        when(serviceMock.listMedics(any())).thenReturn(awaitedResponse);
        var response = controllerMock.perform(get(controllerEndpoint)).andReturn().getResponse();

        var awaitedJson = jsonMedicList.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void medicById() throws Exception {

        var awaitedResponse = new GetMedicDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setName("medic test");
        awaitedResponse.setEmail("medic@test.com");
        awaitedResponse.setCrm("123456");
        awaitedResponse.setSpecialty(SpecialtysEnum.GYNECOLOGY);

        when(serviceMock.queryMedicById(1)).thenReturn(awaitedResponse);
        var response = controllerMock.perform(get(controllerEndpoint + "/1")).andReturn().getResponse();

        var awaitedJson = jsonMedicId.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void updateMedic() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("88");
        addressRequest.setComplement("Complement Test");

        var request = new UpdateMedicRequestDTO();
        request.setId(1);
        request.setName("update medic");
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

        var awaitedResponse = new UpdateMedicResponseDTO();
        awaitedResponse.setId(request.getId());
        awaitedResponse.setName(request.getName());
        awaitedResponse.setPhone(request.getPhone());
        awaitedResponse.setAddress(addressResponse);

        when(serviceMock.updateMedic(request)).thenReturn(awaitedResponse);
        var response = controllerMock.perform(put(controllerEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicUpdateRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonMedicUpdateResponse.write(awaitedResponse).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    @WithMockUser
    public void updateUnactiveMedic() throws Exception {

        var addressRequest = new SaveAddressDTO();
        addressRequest.setStreet("Avenue Test");
        addressRequest.setDistrict("District Test");
        addressRequest.setCity("City Test");
        addressRequest.setZipCode("00000-000");
        addressRequest.setState("UF");
        addressRequest.setNumber("88");
        addressRequest.setComplement("Complement Test");

        var request = new UpdateMedicRequestDTO();
        request.setId(1);
        request.setName("update medic");
        request.setPhone("22990073209");
        request.setAddress(addressRequest);

        var addressModel = addressMapper.addressDtoToModel(addressRequest);

        var entity = new MedicEntity();
        entity.setId(1);
        entity.setName("test");
        entity.setPhone("11990023201");
        entity.setEmail("update@test.com.br");
        entity.setCrm("654321");
        entity.setSpecialty(SpecialtysEnum.ORTHOPEDICS);
        entity.setAddress(addressModel);
        entity.setActive(false);
        entity.setCreated_at(LocalDateTime.now());
        entity.setModified_at(LocalDateTime.now());

        repository.save(entity);

        when(serviceMock.updateMedic(request)).thenThrow(UnactiveException.class);

        var mockRequest = controllerMock.perform(put(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMedicUpdateRequest.write(request).getJson()))
                .andReturn();

        var status = mockRequest.getResponse().getStatus();
        var exceptionCaught = mockRequest.getResolvedException().getClass();


        assertEquals(status, HttpStatus.BAD_REQUEST.value());
        assertEquals(exceptionCaught, UnactiveException.class);
    }

    @Test
    @WithMockUser
    public void deleteMedic() throws Exception {
        var response = controllerMock.perform(delete(controllerEndpoint + "/1")).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }
}