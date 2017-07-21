package com.me.squad.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.squad.newproject.adapter.AccountsAdapter;
import com.me.squad.newproject.model.Account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView accountsRecyclerView;
    private AccountsAdapter accountsAdapter;
    private List<Account> accountsList = new ArrayList<>();
    private boolean mockDate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountsRecyclerView = (RecyclerView) findViewById(R.id.accounts_recycler_view);
        accountsAdapter = new AccountsAdapter(accountsList);
        accountsRecyclerView.setAdapter(accountsAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        accountsRecyclerView.setLayoutManager(mLayoutManager);

        if(mockDate) {
            Account a = new Account();
            a.setAccountName("Test A");
            a.setAccountBalance(2000);
            accountsList.add(a);
            accountsList.add(a);
            accountsList.add(a);
        } else {
            // TODO Real data!!!
        }

        accountsAdapter.notifyDataSetChanged();
    }
}
