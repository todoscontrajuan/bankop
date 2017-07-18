package com.me.squad.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.me.squad.newproject.adapter.AccountsAdapter;
import com.me.squad.newproject.adapter.TransactionsAdapter;
import com.me.squad.newproject.model.Account;
import com.me.squad.newproject.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListView listView;
    private AccountsAdapter adapter;
    private TransactionsAdapter adapter2;
    private List<Account> accountList;
    private ArrayList<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        accountList = new ArrayList<>();
        adapter = new AccountsAdapter(accountList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        Account a = new Account();
        a.setAccountName("Test A");
        a.setBalance(2000);
        accountList.add(a);
        accountList.add(a);
        accountList.add(a);

        adapter.notifyDataSetChanged();

        View v = LayoutInflater.from(this).inflate(R.layout.account_card, null);
        listView = (ListView) v.findViewById(R.id.list_view);
        transactionList = new ArrayList<>();

        Transaction b = new Transaction();
        b.setNote("Alto McDonalds");
        b.setAmount(300);
        transactionList.add(b);

        adapter2 = new TransactionsAdapter(this.getApplicationContext(), transactionList);
        listView.setAdapter(adapter2);
    }
}
