package com.me.squad.bankop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.me.squad.bankop.adapter.TransactionsAdapter;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.model.Transaction;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactionsList = new ArrayList<>();
    private Account account;
    private LinearLayout noTransactionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Intent i = getIntent();
        account = (Account) i.getSerializableExtra("currentAccount");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(account.getAccountName());
        }

        noTransactionsLayout = (LinearLayout) findViewById(R.id.no_transactions_layout);
        loadTransactions();
    }

    private void loadTransactions() {
        try {
            Dao<Transaction, Integer> transactionDao = GeneralUtils.getHelper(getApplicationContext()).getTransactionDao();
            QueryBuilder<Transaction, Integer> queryBuilder = transactionDao.queryBuilder();
            Where<Transaction, Integer> where = queryBuilder.where();
            where.or(where.eq("transaction_source", account.getAccountId()),
                    where.eq("transaction_destination", account.getAccountId()));
            queryBuilder.orderBy("transaction_date", false);
            queryBuilder.limit(60);
            PreparedQuery<Transaction> preparedQuery = queryBuilder.prepare();
            for (Transaction transaction : transactionDao.query(preparedQuery)) {
                transactionsList.add(transaction);
            }
            if(transactionsList.size() == 0) {
                noTransactionsLayout.setVisibility(View.VISIBLE);
            } else {
                noTransactionsLayout.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ListView transactionsListView = (ListView) findViewById(R.id.transactions_list_view);
        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(getApplicationContext(), transactionsList);
        transactionsListView.setAdapter(transactionsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        transactionsList = new ArrayList<>();
        loadTransactions();
    }
}
