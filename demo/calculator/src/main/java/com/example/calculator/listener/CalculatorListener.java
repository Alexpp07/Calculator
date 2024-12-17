package com.example.calculator.listener;

import com.example.calculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CalculatorListener {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "calculator-requests", groupId = "calculator-group")
    public void onMessage(String message, @Header("kafka_receivedMessageKey") String requestId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payload = mapper.readValue(message, Map.class);

        String operation = (String) payload.get("operation");
        BigDecimal a = new BigDecimal(payload.get("a").toString());
        BigDecimal b = new BigDecimal(payload.get("b").toString());
        BigDecimal result;

        switch (operation) {
            case "sum":
                result = calculatorService.sum(a, b);
                break;
            case "subtract":
                result = calculatorService.subtract(a, b);
                break;
            case "multiply":
                result = calculatorService.multiply(a, b);
                break;
            case "divide":
                result = calculatorService.divide(a, b);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        kafkaTemplate.send(new ProducerRecord<>("calculator-responses", requestId, result.toString()));
    }
}
