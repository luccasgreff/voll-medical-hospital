package com.hosp.med.voll.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hosp.med.voll.domain.model.dto.authentication.AuthenticationDTO;
import com.hosp.med.voll.domain.model.dto.authentication.AuthenticationResponseDTO;
import com.hosp.med.voll.domain.model.dto.authentication.LoginDTO;
import com.hosp.med.voll.domain.model.dto.authentication.LoginResponseDTO;
import com.hosp.med.voll.service.AuthenticationService;
import com.hosp.med.voll.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthenticationControllerTest {

    @Autowired
    private MockMvc controllerMock;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private AuthenticationService authenticationServiceMock;

    @Autowired
    private JacksonTester<LoginDTO> jsonLoginRequest;

    @Autowired
    private JacksonTester<LoginResponseDTO> jsonLoginResponse;

    @Autowired
    private JacksonTester<AuthenticationDTO> jsonAuthenticationRequest;

    @Autowired
    private JacksonTester<AuthenticationResponseDTO> jsonAuthenticationResponse;

    private String controllerEndpoint = "/user";

    private String controllerAuthenticationEndpoint = "/user/authenticate";


    @Test
    public void registerUser() throws Exception {

        var request = new LoginDTO();
        request.setLogin("user.test");
        request.setPassword("123456789");

        var awaitedResponse = new LoginResponseDTO();
        awaitedResponse.setId(1);
        awaitedResponse.setLogin(request.getLogin());
        awaitedResponse.setPassword(request.getPassword());

        when(userServiceMock.registerUser(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLoginRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonLoginResponse.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    public void signIn() throws Exception {

        var request = new AuthenticationDTO();
        request.setLogin("user.test");
        request.setPassword("123456789");

        var algorithm = Algorithm.HMAC256(request.getPassword());
        var token = JWT.create()
                        .withIssuer("Test")
                        .withSubject(request.getLogin())
                        .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                        .sign(algorithm);

        var awaitedResponse = new AuthenticationResponseDTO();
        awaitedResponse.setToken(token);

        when(authenticationServiceMock.generateToken(request)).thenReturn(awaitedResponse);

        var response = controllerMock.perform(post(controllerAuthenticationEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthenticationRequest.write(request).getJson()))
                .andReturn().getResponse();

        var awaitedJson = jsonAuthenticationResponse.write(awaitedResponse).getJson();


        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), awaitedJson);
    }

    @Test
    public void signInInvalidCredentials() throws Exception {

        var request = new AuthenticationDTO();
        request.setLogin("user.test");
        request.setPassword("123456789");


        when(authenticationServiceMock.generateToken(request)).thenThrow(BadCredentialsException.class);

        var mockRequest = controllerMock.perform(post(controllerAuthenticationEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthenticationRequest.write(request).getJson()))
                .andReturn();

        var status = mockRequest.getResponse().getStatus();
        var exceptionCaught = mockRequest.getResolvedException().getClass();

        assertEquals(status, HttpStatus.UNAUTHORIZED.value());
        assertEquals(exceptionCaught, BadCredentialsException.class);
    }

}