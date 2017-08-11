package com.me.squad.bankop.model;

import java.io.Serializable;

/**
 * Created by juan_ on 06/07/2017.
 */

public class Account implements Serializable {
    private String accountName;
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
}
