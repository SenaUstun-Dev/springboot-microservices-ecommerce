package com.github.senaustundev.notification_service.service.impl;

import com.github.senaustundev.notification_service.event.OrderPlacedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceUnitTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private OrderPlacedEvent event;

    @BeforeEach
    void setUp() {
        event = new OrderPlacedEvent();
        event.setOrderNumber("ORD123");
        event.setEmail("customer@example.com");
        event.setFirstName("John");
        event.setLastName("Doe");
    }

    @Test
    void shouldSendOrderPlacedNotification_Success() {
        // When
        notificationService.sendOrderPlacedNotification(event);

        // Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @Test
    void shouldThrowRuntimeException_WhenMailExceptionOccurs() {
        // Given
        doThrow(new MailSendException("Email service down"))
                .when(javaMailSender).send(any(MimeMessagePreparator.class));

        // When & Then
        assertThatThrownBy(() -> notificationService.sendOrderPlacedNotification(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Exception occurred when sending mail to customer@example.com");
        
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }
}
