package com.github.senaustundev.notification_service;

import com.github.senaustundev.notification_service.event.OrderPlacedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer"
        })
class NotificationServiceIntegrationTest {

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendNotificationWhenOrderPlaced() {
        // Given
        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setOrderNumber("ORD-999");
        event.setEmail("test@test.com");
        event.setFirstName("Alice");
        event.setLastName("Wonderland");

        // When
        kafkaTemplate.send("order-placed", event);

        // Then
        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                    verify(javaMailSender, atLeastOnce()).send(any(MimeMessagePreparator.class));
                });
    }
}
