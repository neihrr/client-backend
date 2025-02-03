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

    @Value("${ACCOUNT_MANAGER_URL:http://localhost:8090}")
    private String accountManagerUrl; // Base URL of the Account Manager application

    public TransactionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    /**
     * Periodically generates and sends random transactions.
     */
    @Scheduled(fixedRate = 10000) // Executes every 5 seconds
    public void generateAndSendTransaction() {
        try {
            // Send POST request to Account Manager application
            // Randomly select an account ID (you can adjust this logic)
            int delay = random.nextInt(10) + 1; // Random value between 1 and 10 seconds
            Thread.sleep(delay * 1000L);

            int accountId = random.nextInt(4) + 1;

            // Randomly determine transaction type and amount
            TransactionType type = random.nextBoolean() ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;

            double amount = type == TransactionType.DEPOSIT
                    ? random.nextDouble() * 10// Deposit amounts up to $1000
                    : -random.nextDouble() * 5; // Withdrawal amounts up to $500

            TransactionRequest transactionRequest = new TransactionRequest(type, Math.abs(amount));

            // Construct URL
            String url = accountManagerUrl + "/transactions/process/"+ accountId ;
            TransactionResponse response = restTemplate.postForObject(url, transactionRequest, TransactionResponse.class);
            System.out.println("Transaction processed: " + response);
        }catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to process transaction: " + e.getMessage());
        }
    }
}