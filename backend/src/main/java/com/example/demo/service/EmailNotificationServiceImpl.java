package com.example.demo.service;

import com.example.demo.client.EmailClient;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.EmailNotificationException;
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
public class EmailNotificationServiceImpl implements NotificationService {

    @Autowired
    EmailClient emailClient;

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    @Override
    public Notification sendNotification(Customer customer, String message) {
        // Logic to send email notification
        try {
            logger.info("Sending Email to customer ID: {}, and message: {}", customer.getId() + customer.getName() , message);
            Notification notification = Notification.builder()
                    .message(message)
                    .customerId(customer.getId())
                    .timestamp(LocalDateTime.now())
                    .notificationType(NotificationTypeEnum.EMAIL)
                    .build();
            this.emailClient.sendEmail(customer, notification);
            return notification;
        } catch (Exception e) {
            throw new EmailNotificationException("Failed to send Email to customer " + customer.getId());
        }
    }
}

