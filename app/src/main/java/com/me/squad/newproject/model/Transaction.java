package com.me.squad.newproject.model;

import java.util.Date;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Transaction {
    int amount;
    Date date;
    String Note;
    TransactionType transactiontype;
    Account account;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public TransactionType getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(TransactionType transactiontype) {
        this.transactiontype = transactiontype;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
