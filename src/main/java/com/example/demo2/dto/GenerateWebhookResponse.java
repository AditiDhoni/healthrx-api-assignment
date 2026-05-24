package com.example.demo2.dto;

import lombok.Data;

@Data
public class GenerateWebhookResponse {

    private String webhook;
    private String accessToken;
}