package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.LoginUserDto;
import com.alkemy.wallet.dto.RequestUserDto;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Role;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.CustomUserDetailsService;
import com.alkemy.wallet.service.interfaces.IRoleService;
import com.alkemy.wallet.service.interfaces.IUserService;
import com.alkemy.wallet.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import({ObjectMapper.class, AuthController.class})
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public IUserService userService;

    @Autowired
    IRoleService roleService;

    User user;

    Role role;

    Account accountArs;

    Account accountUsd;

    RequestUserDto requestUserDto;

    String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .password("test")
                .creationDate(new Date())
                .role(role)
                .build();
    }

    @DisplayName("Signing up success")
    @Test
    void signUpSuccess() throws Exception {

        requestUserDto = RequestUserDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .password("test").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isCreated()).andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("test")))
                .andExpect(jsonPath("$.lastName", is("test")))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.token", not(Matchers.blankString())));
    }

    @DisplayName("Signing up failed notNull nulled")
    @Test
    void signUpFail() throws Exception {

        requestUserDto = RequestUserDto.builder()
                .firstName("test")
                .lastName(null)
                .email("test@test.com")
                .password("test").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Signing in success")
    @Test
    void signInSuccess() throws Exception {

        requestUserDto = RequestUserDto.builder()
                .firstName("user")
                .lastName("user")
                .email("user@user.com")
                .password("user").build();

        customUserDetailsService.save(requestUserDto);

        LoginUserDto loginDto = LoginUserDto.builder()
                .email("user@user.com")
                .password("user").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("Signing in failed wrong pass")
    @Test
    void signInFailed() throws Exception {
        requestUserDto = RequestUserDto.builder()
                .firstName("user_fail")
                .lastName("user_fail")
                .email("user_fail@user.com")
                .password("user_fail").build();

        customUserDetailsService.save(requestUserDto);

        LoginUserDto loginDto = LoginUserDto.builder()
                .email("user_fail@user.com")
                .password("cualquiercosa").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isForbidden());
    }
}