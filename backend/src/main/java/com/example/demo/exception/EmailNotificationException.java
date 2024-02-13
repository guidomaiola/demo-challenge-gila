package com.example.demo.exception;

public class EmailNotificationException extends RuntimeException {
    public EmailNotificationException(String message) {
        super(message);
    }
}
