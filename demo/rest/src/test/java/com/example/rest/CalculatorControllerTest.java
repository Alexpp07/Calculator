/*package com.example.rest;

import com.example.calculator.service.CalculatorService;
import com.example.rest.controller.CalculatorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    void sum_ShouldReturnResult() throws Exception {
        when(calculatorService.sum(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
                .thenReturn(BigDecimal.valueOf(5));

        mockMvc.perform(get("/sum")
                .param("a", "2")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5));

        verify(calculatorService, times(1)).sum(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void subtract_ShouldReturnResult() throws Exception {
        when(calculatorService.subtract(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
                .thenReturn(BigDecimal.valueOf(-1));

        mockMvc.perform(get("/subtract")
                .param("a", "2")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-1));

        verify(calculatorService, times(1)).subtract(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void multiply_ShouldReturnResult() throws Exception {
        when(calculatorService.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
                .thenReturn(BigDecimal.valueOf(6));

        mockMvc.perform(get("/multiply")
                .param("a", "2")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(6));

        verify(calculatorService, times(1)).multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void divide_ShouldReturnResult() throws Exception {
        when(calculatorService.divide(BigDecimal.valueOf(2), BigDecimal.valueOf(1)))
                .thenReturn(BigDecimal.valueOf(2.000).setScale(3));

        mockMvc.perform(get("/divide")
                .param("a", "2")
                .param("b", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.000));

        verify(calculatorService, times(1)).divide(BigDecimal.valueOf(2), BigDecimal.valueOf(1));
    }

    @Test
    void divide_ShouldReturnBadRequestWhenDividingByZero() throws Exception {
        when(calculatorService.divide(any(BigDecimal.class), eq(BigDecimal.ZERO)))
                .thenThrow(new IllegalArgumentException("Division by zero is not allowed"));

        mockMvc.perform(get("/divide")
                .param("a", "6")
                .param("b", "0"))
                .andExpect(status().isBadRequest()) // Updated to check for BAD REQUEST (400)
                .andExpect(jsonPath("$.error").value("Division by zero is not allowed"));
    }

}*/
