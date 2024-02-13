package com.example.demo;

import com.example.demo.dto.NotificationDto;
import com.example.demo.enums.CategoryType;
import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.dto.MessageDto;
import com.example.demo.model.Notification;
import com.example.demo.model.Customer;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private SmsNotificationServiceImpl smsNotificationService;

    @Mock
    private EmailNotificationServiceImpl emailNotificationService;

    @Mock
    private PushNotificationServiceImpl pushNotificationService;

    private MessageService messageService;

    private MessageDto message;

    private NotificationDto notificationDto;

    private Notification notification;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(
                smsNotificationService,
                emailNotificationService,
                pushNotificationService,
                customerRepository,
                notificationRepository
        );

        // Prepare test data
        customer = new Customer();
        customer.setId(1L);
        customer.setSubscribedCategories(List.of(CategoryType.SPORTS, CategoryType.MOVIES));
        List<Customer> customers = List.of(customer);

        message = new MessageDto();
        message.setCategory(CategoryType.SPORTS);
        message.setMessage("Test message");

        notificationDto = NotificationDto.builder()
                .message(message.getMessage())
                .customerId(customer.getId())
                .timestamp(LocalDateTime.now().toString())
                .build();

        notification = mock(Notification.class);
        when(notification.toDto()).thenReturn(notificationDto);

        // Mock customerRepository behavior
        when(customerRepository.findAll()).thenReturn(customers);
    }

    @Test
    void testSendEmailNotification() {
        notificationDto.setNotificationType(NotificationTypeEnum.EMAIL.name());
        List<NotificationDto> expectedNotifications = List.of(notificationDto);

        customer.setNotificationChannels(List.of(NotificationTypeEnum.EMAIL));

        // Mock notificationService behavior
        when(emailNotificationService.sendNotification(any(Customer.class), anyString()))
                .thenReturn(notification);

        // Call the method under test
        List<NotificationDto> actualNotifications = messageService.sendNotification(message);

        // Verify that the method behaves as expected
        assertEquals(expectedNotifications, actualNotifications);

        // Verify interactions
        // Verify used EMAIL notificationService
        verify(emailNotificationService, times(1)).sendNotification(eq(customer), eq(message.getMessage()));
        verify(customerRepository, times(1)).findAll();
        verify(notificationRepository, times(1)).save(eq(notification));
    }

    @Test
    void testSendPushNotification() {
        notificationDto.setNotificationType(NotificationTypeEnum.PUSH.name());
        List<NotificationDto> expectedNotifications = List.of(notificationDto);

        customer.setNotificationChannels(List.of(NotificationTypeEnum.PUSH));

        // Mock notificationService behavior
        when(pushNotificationService.sendNotification(any(Customer.class), anyString()))
                .thenReturn(notification);

        // Call the method under test
        List<NotificationDto> actualNotifications = messageService.sendNotification(message);

        // Verify that the method behaves as expected
        assertEquals(expectedNotifications, actualNotifications);

        // Verify interactions
        // Verify used PUSH notificationService
        verify(pushNotificationService, times(1)).sendNotification(eq(customer), eq(message.getMessage()));
        verify(customerRepository, times(1)).findAll();
        verify(notificationRepository, times(1)).save(eq(notification));
    }

    @Test
    void testSendPushNotificationShouldNotUseEmailNotificationService() {
        notificationDto.setNotificationType(NotificationTypeEnum.PUSH.name());
        List<NotificationDto> expectedNotifications = List.of(notificationDto);

        customer.setNotificationChannels(List.of(NotificationTypeEnum.PUSH));

        // Mock notificationService behavior
        when(pushNotificationService.sendNotification(any(Customer.class), anyString()))
                .thenReturn(notification);

        // Call the method under test
        List<NotificationDto> actualNotifications = messageService.sendNotification(message);

        // Verify that the method behaves as expected
        assertEquals(expectedNotifications, actualNotifications);

        // Verify interactions
        // Verify used EMAIL notificationService
        verify(emailNotificationService, times(0)).sendNotification(eq(customer), eq(message.getMessage()));
        verify(customerRepository, times(1)).findAll();
        verify(notificationRepository, times(1)).save(eq(notification));
    }

}
