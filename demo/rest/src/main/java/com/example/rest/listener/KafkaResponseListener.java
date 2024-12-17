package com.example.rest.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaResponseListener {

    private final ConcurrentHashMap<String, String> responses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CountDownLatch> latches = new ConcurrentHashMap<>();

    public String sendAndWait(String requestId) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        latches.put(requestId, latch);

        // Espera pela resposta com um timeout (5 segundos neste caso)
        if (latch.await(5, TimeUnit.SECONDS)) {
            return responses.get(requestId);
        } else {
            throw new RuntimeException("Timeout waiting for response from calculator module.");
        }
    }

    @KafkaListener(topics = "calculator-responses", groupId = "rest-group")
    public void onMessage(ConsumerRecord<String, String> record) {
        String requestId = record.key();
        String result = record.value();

        // Propaga o ID da requisição para o MDC nos logs
        MDC.put("requestId", requestId);

        responses.put(requestId, result);

        CountDownLatch latch = latches.get(requestId);
        if (latch != null) {
            latch.countDown();
        }

        MDC.remove("requestId");
    }
}
