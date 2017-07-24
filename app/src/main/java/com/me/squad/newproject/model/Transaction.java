package com.me.squad.newproject.model;

import java.util.Date;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Transaction {

    private double transactionAmount;
    private Date transactionDate;
    private String transactionNote;
    private TransactionType transactionType;
    private Account transactionAccount;

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(Account transactionAccount) {
        this.transactionAccount = transactionAccount;
    }
}
