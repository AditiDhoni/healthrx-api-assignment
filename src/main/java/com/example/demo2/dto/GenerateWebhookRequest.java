package com.example.demo2.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateWebhookRequest {

    private String name;
    private String regNo;
    private String email;
}