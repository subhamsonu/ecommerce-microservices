package com.smartmart.inventory_service.kafka;

import com.smartmart.inventory_service.dto.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryProducer {


    private static final Logger log = LoggerFactory.getLogger(InventoryProducer.class);
    private static final String TOPIC = "inventory-topic";

    private final KafkaTemplate<String, OrderEventDto> kafkaTemplate;

    public void sendOrderAfterInventoryReserved(OrderEventDto orderEvent) {
        try {
            log.info("Sending order event to Kafka topic '{}': orderId={}", TOPIC, orderEvent.getOrderId());
            kafkaTemplate.send(TOPIC, orderEvent);
            log.debug("Order event sent successfully for orderId: {}", orderEvent.getOrderId());
        } catch (Exception e) {
            log.error("Error sending order event for orderId: {}: {}", orderEvent.getOrderId(), e.getMessage(), e);
            throw new RuntimeException("Failed to send order event: " + e.getMessage(), e);
        }
    }



}
