package com.me.squad.newproject.model;

import java.util.Date;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Transaction {

    private double transactionAmount;
    private long transactionDateInMilliseconds;
    private String transactionNote;
    private TransactionType transactionType;
    private Account transactionSourceAccount;
    private Account transactionDestinationAccount;

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public long getTransactionDateInMilliseconds() {
        return transactionDateInMilliseconds;
    }

    public void setTransactionDateInMilliseconds(long transactionDateInMilliseconds) {
        this.transactionDateInMilliseconds = transactionDateInMilliseconds;
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

    public Account getTransactionSourceAccount() {
        return transactionSourceAccount;
    }

    public void setTransactionSourceAccount(Account transactionSourceAccount) {
        this.transactionSourceAccount = transactionSourceAccount;
    }

    public Account getTransactionDestinationAccount() {
        return transactionDestinationAccount;
    }

    public void setTransactionDestinationAccount(Account transactionDestinationAccount) {
        this.transactionDestinationAccount = transactionDestinationAccount;
    }
}
