package com.example.demo;

import com.example.demo.controller.MessagesController;
import com.example.demo.dto.MessageDto;
import com.example.demo.dto.NotificationDto;
import com.example.demo.enums.CategoryType;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessagesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessagesController messagesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messagesController).build();
    }

    @Test
    public void testSendMessage() throws Exception {
        // Prepare test data
        MessageDto messageDto = new MessageDto(CategoryType.SPORTS, "Test message");
        List<NotificationDto> expectedNotifications = new ArrayList<>();

        // Mock service method
        when(messageService.sendNotification(any(MessageDto.class))).thenReturn(expectedNotifications);

        // Perform the POST request
        mockMvc.perform(post("/messages/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(messageDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSendMessageShouldFailDueValidation() throws Exception {
        // Prepare test data
        MessageDto messageDto = new MessageDto();


        // Perform the POST request
        messagesController.sendMessage(MessageDto.builder().build());
    }

    // Utility method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


