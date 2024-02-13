package com.example.demo.service;

import com.example.demo.client.PushClient;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.PushNotificationException;
import com.example.demo.model.Notification;
import com.example.demo.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
public class PushNotificationServiceImpl implements NotificationService {

    @Autowired
    PushClient pushClient;

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);

    @Override
    public Notification sendNotification(Customer customer, String message) {
        try {
            logger.info("Sending Push notification to customer ID: {}, and message: {}", customer.getId() + customer.getName() , message);
            Notification notification = Notification.builder()
                    .message(message)
                    .customerId(customer.getId())
                    .timestamp(LocalDateTime.now())
                    .notificationType(NotificationTypeEnum.PUSH)
                    .build();
            this.pushClient.sendPushNotification(customer, notification);
            return notification;
        } catch (Exception e) {
            throw new PushNotificationException("Failed to send Push notification to customer " + customer.getId());
        }
    }
}
