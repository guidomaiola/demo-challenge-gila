package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.model.Customer;

public interface NotificationService {
    Notification sendNotification(Customer customer, String message);
}
