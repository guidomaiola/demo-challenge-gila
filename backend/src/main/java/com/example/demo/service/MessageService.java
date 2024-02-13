package com.example.demo.service;

import com.example.demo.dto.NotificationDto;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.exception.EmailNotificationException;
import com.example.demo.exception.PushNotificationException;
import com.example.demo.exception.SmsNotificationException;
import com.example.demo.dto.MessageDto;
import com.example.demo.model.Notification;
import com.example.demo.model.Customer;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    @Autowired
    private SmsNotificationServiceImpl smsNotificationService;

    @Autowired
    private EmailNotificationServiceImpl emailNotificationService;

    @Autowired
    private PushNotificationServiceImpl pushNotificationService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);


    public List<NotificationDto> sendNotification(MessageDto message) {

        List<Customer> customers = customerRepository.findAll();
        List<NotificationDto> notificationDtos = new ArrayList<NotificationDto>();
        logger.info("There are {} customers registered.", customers.size());
        for (Customer customer : customers) { // Iterate customers
            if (customer.getSubscribedCategories().contains(message.getCategory())) { // If the message is one of the subscribed Category
                logger.info("The customer {} is registered to Category {}.", customer.getId() + customer.getName(), message.getCategory());
                for (NotificationTypeEnum channel : customer.getNotificationChannels()) { // Iterate notification channels
                    NotificationService notificationService = getNotificationServiceByType(channel);
                    if (notificationService != null) {
                        try {
                            Notification notification = notificationService.sendNotification(customer, message.getMessage()); // Notify using proper channel
                            notificationRepository.save(notification);
                            notificationDtos.add(notification.toDto());
                        } catch (EmailNotificationException e) {
                            logger.error("Failed to send e-mail notification to customer {}", customer.getId(), e);
                        } catch (SmsNotificationException e) {
                            logger.error("Failed to send SMS notification to customer {}", customer.getId(), e);
                        } catch (PushNotificationException e) {
                            logger.error("Failed to send push notification to customer {}", customer.getId(), e);
                        }

                    }
                }
            }
        }
        return notificationDtos;
    }

    private NotificationService getNotificationServiceByType(NotificationTypeEnum notificationType) {
        switch (notificationType) {
            case SMS:
                return this.smsNotificationService;
            case PUSH:
                return this.pushNotificationService;
            case EMAIL:
                return this.emailNotificationService;
            default:
                return null;
        }
    }


    public List<NotificationDto> getNotifications() {
        return this.notificationRepository.findAll().stream().map(Notification::toDto).collect(Collectors.toList());
    }
}
