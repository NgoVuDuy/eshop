package com.nvd.electroshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.exception.ConflictException;
import com.nvd.electroshop.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    // Đăng ký thành công
    void register_withValidRequest_shouldResultSuccess() throws Exception {

        AuthRequest authRequest = AuthRequest.builder()
                .username("Ngovuduy")
                .password("Ngovuduy")
                .build();

        String content = objectMapper.writeValueAsString(authRequest);

        Message message = new Message(1, "Tạo tài khoản thành công");

        Mockito.when(authService.register(Mockito.any(AuthRequest.class))).thenReturn(message);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tạo tài khoản thành công"));
    }
    // Đăng ký thất bại - trùng username
    @Test
    void register_withDuplicateUsername_shouldReturnFail() throws Exception {

        //Arrange
        AuthRequest authRequest = AuthRequest.builder()
                .username("Ngovuduy")
                .password("Ngovuduy")
                .build();
        Message message = new Message(0, "Tên tài khoản đã tồn tại");
        //Mock
        Mockito.when(authService.register(Mockito.any(AuthRequest.class))).thenThrow(new ConflictException("Tên tài khoản đã tồn tại"));
        //Act - assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tên tài khoản đã tồn tại"));

    }
    // Đăng ký thất bại - invalid
}