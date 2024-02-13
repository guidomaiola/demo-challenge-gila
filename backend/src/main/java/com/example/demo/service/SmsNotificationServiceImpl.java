package com.example.demo.service;

import com.example.demo.client.SMSClient;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.SmsNotificationException;
import com.example.demo.model.Notification;
import com.example.demo.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Service
public class SmsNotificationServiceImpl implements NotificationService {

    @Autowired
    SMSClient smsClient;

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationServiceImpl.class);

    @Override
    public Notification sendNotification(Customer customer, String message) {
        // Logic to send SMS notification
        try {
            logger.info("Sending SMS notification to customer ID: {}, and message: {}", customer.getId() + customer.getName() , message);
            Notification notification = Notification.builder()
                    .message(message)
                    .customerId(customer.getId())
                    .timestamp(LocalDateTime.now())
                    .notificationType(NotificationTypeEnum.SMS)
                    .build();
            this.smsClient.sendSMS(customer, notification);
            return notification;
        } catch (Exception e) {
            throw new SmsNotificationException("Failed to send SMS notification to customer " + customer.getId());
        }


    }
}
