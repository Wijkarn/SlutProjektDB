package models;

import java.sql.*;

public class Account {

    private int accountId;
    private int ownerId;
    private long balance;
    private String accountNumber;
    private Timestamp created;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Account() {

    }

    public Account(int accountId, int ownerId, int balance, String accountNumber, Timestamp created) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.created = created;
    }
}
