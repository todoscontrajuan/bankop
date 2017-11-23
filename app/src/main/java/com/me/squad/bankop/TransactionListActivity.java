package com.me.squad.bankop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.me.squad.bankop.adapter.TransactionsAdapter;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.model.Transaction;
import com.me.squad.bankop.model.TransactionType;

import java.util.ArrayList;
import java.util.Date;

public class TransactionListActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactionsList = new ArrayList<>();
    private boolean mockMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Intent i = getIntent();
        Account account = (Account) i.getSerializableExtra("currentAccount");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(account.getAccountName());
        }

        if(mockMode) {
            Transaction a = new Transaction();
            a.setTransactionAmount(2000);
            a.setTransactionNote("Hola");
            a.setTransactionDate(new Date());
            a.setTransactionType(TransactionType.EXPENSE);
            a.setTransactionSourceAccount(account);
            transactionsList.add(a);

            Transaction b = new Transaction();
            b.setTransactionAmount(300);
            b.setTransactionNote("Hola this is a long thing to see how the note is behaving with a long text");
            b.setTransactionDate(new Date());
            b.setTransactionType(TransactionType.INCOME);
            b.setTransactionSourceAccount(account);
            transactionsList.add(b);

            Transaction c = new Transaction();
            c.setTransactionAmount(500);
            c.setTransactionNote("Transfer test");
            c.setTransactionDate(new Date());
            c.setTransactionType(TransactionType.TRANSFER);
            c.setTransactionSourceAccount(account);
            Account d = new Account();
            d.setAccountName("Dolares");
            c.setTransactionDestinationAccount(d);
            transactionsList.add(c);
        } else {
            // TODO Search in the database for the transactions for this account
        }

        ListView transactionsListView = (ListView) findViewById(R.id.transactions_list_view);
        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(getApplicationContext(), transactionsList);
        transactionsListView.setAdapter(transactionsAdapter);
    }
}
