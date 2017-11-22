package com.me.squad.bankop.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Transaction implements Serializable {

    @DatabaseField(generatedId = true, columnName = "transaction_id")
    private int transactionId;

    @DatabaseField(columnName = "transaction_amount")
    private double transactionAmount;

    @DatabaseField(columnName = "transaction_date")
    private long transactionDateInMilliseconds;

    @DatabaseField(columnName = "transaction_note")
    private String transactionNote;

    @DatabaseField(dataType = DataType.ENUM_INTEGER, columnName = "transaction_type")
    private TransactionType transactionType;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "transaction_source")
    private Account transactionSourceAccount;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "transaction_destination")
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

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
