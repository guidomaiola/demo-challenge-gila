package com.example.demo.model;

import com.example.demo.enums.CategoryType;
import com.example.demo.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private List<CategoryType> subscribedCategories;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private List<NotificationTypeEnum> notificationChannels;

}