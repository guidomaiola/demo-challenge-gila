package com.example.demo.controller;

import com.example.demo.dto.NotificationDto;
import com.example.demo.dto.MessageDto;
import com.example.demo.service.MessageService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class MessagesController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/messages/send")
    public ResponseEntity<List<NotificationDto>> sendMessage(@Valid @RequestBody MessageDto message) {
        List<NotificationDto> notificationDtos = messageService.sendNotification(message);
        return ResponseEntity.ok(notificationDtos);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        return ResponseEntity.ok(messageService.getNotifications());
    }
}