package com.example.calculator;

import com.example.calculator.serviceImpl.CalculatorServiceImpl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceImplTest {
    private final CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();

    @Test
    void add_ShouldReturnSumOfTwoNumbers() {
        BigDecimal result = calculatorService.sum(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(5), result);
    }

    @Test
    void subtract_ShouldReturnDifferenceOfTwoNumbers() {
        BigDecimal result = calculatorService.subtract(BigDecimal.valueOf(5), BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(2), result);
    }

    @Test
    void multiply_ShouldReturnProductOfTwoNumbers() {
        BigDecimal result = calculatorService.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(6), result);
    }

    @Test
    void divide_ShouldReturnQuotientOfTwoNumbers() {
        BigDecimal result = calculatorService.divide(BigDecimal.valueOf(6), BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(2.000).setScale(3), result);
    }

    @Test
    void divide_ShouldThrowExceptionWhenDividingByZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            calculatorService.divide(BigDecimal.valueOf(6), BigDecimal.ZERO)
        );
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
}
