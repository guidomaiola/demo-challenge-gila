package com.example.demo.dto;

import com.example.demo.enums.NotificationTypeEnum;
import com.example.demo.model.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationDto {

    private Long id;
    private String notificationType;
    private Long customerId;
    private String timestamp;
    private String message;

    Notification toModel() {
        return Notification.builder()
                .id(id)
                .notificationType(NotificationTypeEnum.valueOf(notificationType))
                .customerId(customerId)
                .message(message)
                .timestamp(LocalDateTime.parse(timestamp))
                .build();
    }

}