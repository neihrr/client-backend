package com.example.demo;


public class TransactionRequest {
    private int accountId;
    private TransactionType type;
    private double amount;

    public TransactionRequest(int accountId, TransactionType type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    public TransactionRequest() {}

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
