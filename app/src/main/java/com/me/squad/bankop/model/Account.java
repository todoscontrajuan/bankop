package com.me.squad.bankop.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Account implements Serializable {

    @DatabaseField(generatedId = true, columnName = "account_id")
    private int accountId;

    @DatabaseField(columnName = "account_name")
    private String accountName;

    @DatabaseField(columnName = "account_balance")
    private double accountBalance;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
