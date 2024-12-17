package com.example.rest.controller;

import com.example.rest.listener.KafkaResponseListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@RestController
public class CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaResponseListener kafkaResponseListener;

    public CalculatorController(KafkaTemplate<String, String> kafkaTemplate, KafkaResponseListener kafkaResponseListener) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaResponseListener = kafkaResponseListener;
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for sum with a = {} and b = {}", a, b);
        String requestId = UUID.randomUUID().toString();

        MDC.put("requestId", requestId);

        try {
            Map<String, Object> message = new HashMap<>();
            message.put("operation", "sum");
            message.put("a", a);
            message.put("b", b);

            kafkaTemplate.send(new ProducerRecord<>("calculator-requests", requestId, new ObjectMapper().writeValueAsString(message)));

            String response = kafkaResponseListener.sendAndWait(requestId);
            logger.info("Request ID {} - Result of sum is {}", requestId, response);
            
            return ResponseEntity.ok(new HashMap<>() {{
                put("result", response);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            MDC.remove("requestId");
        }
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/subtract")
    public ResponseEntity<?> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for subtract with a = {} and b = {}", a, b);
        String requestId = UUID.randomUUID().toString();

        MDC.put("requestId", requestId);

        try {
            Map<String, Object> message = new HashMap<>();
            message.put("operation", "subtract");
            message.put("a", a);
            message.put("b", b);

            kafkaTemplate.send(new ProducerRecord<>("calculator-requests", requestId, new ObjectMapper().writeValueAsString(message)));

            String response = kafkaResponseListener.sendAndWait(requestId);
            logger.info("Request ID {} - Result of subtract is {}", requestId, response);
            
            return ResponseEntity.ok(new HashMap<>() {{
                put("result", response);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            MDC.remove("requestId");
        }
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/multiply")
    public ResponseEntity<?> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for multiply with a = {} and b = {}", a, b);
        String requestId = UUID.randomUUID().toString();

        MDC.put("requestId", requestId);

        try {
            Map<String, Object> message = new HashMap<>();
            message.put("operation", "multiply");
            message.put("a", a);
            message.put("b", b);

            kafkaTemplate.send(new ProducerRecord<>("calculator-requests", requestId, new ObjectMapper().writeValueAsString(message)));

            String response = kafkaResponseListener.sendAndWait(requestId);
            logger.info("Request ID {} - Result of multiply is {}", requestId, response);
            
            return ResponseEntity.ok(new HashMap<>() {{
                put("result", response);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            MDC.remove("requestId");
        }
    }

    @Operation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "400", description = "Divisão por zero não permitida"),
        @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b){
        logger.info("Received request for divide with a = {} and b = {}", a, b);

        try {
            if (b.compareTo(BigDecimal.ZERO) == 0) {
                throw new IllegalArgumentException("Division by zero is not allowed");
            }
        } catch (IllegalArgumentException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("error", ex.getMessage());
            logger.info("Error dividing a = {} and b = {}", a, b);
            return ResponseEntity.badRequest().body(response);
        }

        String requestId = UUID.randomUUID().toString();

        MDC.put("requestId", requestId);

        try {
            Map<String, Object> message = new HashMap<>();
            message.put("operation", "divide");
            message.put("a", a);
            message.put("b", b);

            kafkaTemplate.send(new ProducerRecord<>("calculator-requests", requestId, new ObjectMapper().writeValueAsString(message)));

            String response = kafkaResponseListener.sendAndWait(requestId);
            logger.info("Request ID {} - Result of divide is {}", requestId, response);
            
            return ResponseEntity.ok(new HashMap<>() {{
                put("result", response);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            MDC.remove("requestId");
        }
    }
}
