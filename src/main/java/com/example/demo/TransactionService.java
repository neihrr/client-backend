package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class TransactionService {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    @Value("http://localhost:8080")
    private String accountManagerUrl; // Base URL of the Account Manager application


    public TransactionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Periodically generates and sends random transactions.
     */
    @Scheduled(fixedRate = 5000) // Executes every 5 seconds
    public void generateAndSendTransaction() {
        // Randomly select an account ID (you can adjust this logic)
        int accountId =  1;

        // Randomly determine transaction type and amount
        TransactionType type = random.nextBoolean() ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;

        double amount = type == TransactionType.DEPOSIT
                ? random.nextDouble() * 1000 // Deposit amounts up to $1000
                : -random.nextDouble() * 500; // Withdrawal amounts up to $500

        TransactionRequest transactionRequest = new TransactionRequest(accountId, type, Math.abs(amount));

        // Construct URL
        String url = accountManagerUrl + "/transactions" ;

        try {
            // Send POST request to Account Manager application
            TransactionResponse response = restTemplate.postForObject(url, transactionRequest, TransactionResponse.class);
            System.out.println("Transaction processed: " + response);
        } catch (Exception e) {
            System.err.println("Failed to process transaction: " + e.getMessage());
        }
    }
}