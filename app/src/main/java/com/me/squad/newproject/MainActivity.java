package com.me.squad.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.squad.newproject.model.Account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private List<Account> accountList;

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

        adapter.notifyDataSetChanged();

    }
}
