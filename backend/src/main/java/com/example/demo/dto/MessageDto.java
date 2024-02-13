package com.example.demo.dto;

import com.example.demo.enums.CategoryType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @NotBlank(message = "Category cannot be null")
    private CategoryType category;

    @NotBlank(message = "Message cannot be blank")
    private String message;
}
