package com.example.demo2;

import com.example.demo2.dto.FinalQueryRequest;
import com.example.demo2.dto.GenerateWebhookRequest;
import com.example.demo2.dto.GenerateWebhookResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StartupRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;

    public StartupRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        // STEP 1 -> Generate Webhook

        String generateUrl =
                "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        GenerateWebhookRequest request =
                new GenerateWebhookRequest(
                        "Aditi Dhoni",
                        "0827IT231008",
                        "aditidhoni231188@acropolis.in"
                );

        ResponseEntity<GenerateWebhookResponse> response =
                restTemplate.postForEntity(
                        generateUrl,
                        request,
                        GenerateWebhookResponse.class
                );

        GenerateWebhookResponse body = response.getBody();

        System.out.println("Webhook URL: " + body.getWebhook());
        System.out.println("Access Token: " + body.getAccessToken());

        // STEP 2 -> SQL QUERY

        String finalSqlQuery = """
                SELECT * FROM employees;
                """;

        // Replace above SQL with your actual answer from Question PDF

        // STEP 3 -> Submit Final Query

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        // IMPORTANT FIX
        headers.set("Authorization", body.getAccessToken());

        FinalQueryRequest finalRequest =
                new FinalQueryRequest(finalSqlQuery);

        HttpEntity<FinalQueryRequest> entity =
                new HttpEntity<>(finalRequest, headers);

        ResponseEntity<String> finalResponse =
                restTemplate.exchange(
                        body.getWebhook(),
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        System.out.println("FINAL RESPONSE:");
        System.out.println(finalResponse.getBody());
    }
}