package com.example.rest.controller;

import com.example.calculator.service.CalculatorService;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for sum with a = {} and b = {}", a, b);
        BigDecimal result = calculatorService.add(a, b);
        logger.info("Result of sum is {}", result);
        return ResponseEntity.ok(new Result(result));
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/subtract")
    public ResponseEntity<?> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for subtract with a = {} and b = {}", a, b);
        BigDecimal result = calculatorService.subtract(a, b);
        logger.info("Result of subtract is {}", result);
        return ResponseEntity.ok(new Result(result));
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/multiply")
    public ResponseEntity<?> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for multiply with a = {} and b = {}", a, b);
        BigDecimal result = calculatorService.multiply(a, b);
        logger.info("Result of multiply is {}", result);
        return ResponseEntity.ok(new Result(result));
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "400", description = "Divisão por zero não permitida"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        logger.info("Received request for divide with a = {} and b = {}", a, b);
        try {
            if (b.compareTo(BigDecimal.ZERO) == 0) {
                throw new IllegalArgumentException("Division by zero is not allowed");
            }
            BigDecimal result = calculatorService.divide(a, b);
            logger.info("Result of divide is {}", result);
            return ResponseEntity.ok(new Result(result));
        } catch (IllegalArgumentException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("error", ex.getMessage());
            logger.info("Error dividing a = {} and b = {}", a, b);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public static class Result {
        private BigDecimal result;

        public Result(BigDecimal result) {
            this.result = result;
        }

        public BigDecimal getResult() {
            return result;
        }

        public void setResult(BigDecimal result) {
            this.result = result;
        }
    }
}
