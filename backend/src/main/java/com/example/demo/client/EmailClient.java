package com.example.demo.client;

import com.example.demo.model.Notification;
import com.example.demo.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class EmailClient {
    public void sendEmail(Customer customer, Notification notification) {
        // send email
    }
}
