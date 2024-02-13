package com.example.demo.model;

import com.example.demo.dto.NotificationDto;
import com.example.demo.enums.NotificationTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private NotificationTypeEnum notificationType;
    private Long customerId;
    private LocalDateTime timestamp;
    private String message;


    public NotificationDto toDto() {
        return NotificationDto.builder()
                .id(id)
                .notificationType(notificationType.name())
                .customerId(customerId)
                .timestamp(timestamp.toString())
                .message(message)
                .build();
    }
}