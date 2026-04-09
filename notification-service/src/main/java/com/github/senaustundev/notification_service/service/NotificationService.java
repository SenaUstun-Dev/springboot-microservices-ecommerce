package com.github.senaustundev.notification_service.service;

import com.github.senaustundev.notification_service.event.OrderPlacedEvent;

public interface NotificationService {

    void sendOrderPlacedNotification(OrderPlacedEvent orderPlacedEvent);
    
}
