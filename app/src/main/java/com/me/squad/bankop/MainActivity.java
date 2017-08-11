package com.me.squad.bankop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.squad.bankop.adapter.AccountsAdapter;
import com.me.squad.bankop.model.Account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView accountsRecyclerView;
    private List<Account> accountsList = new ArrayList<>();
    private boolean mockDate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountsRecyclerView = (RecyclerView) findViewById(R.id.accounts_recycler_view);
        AccountsAdapter accountsAdapter = new AccountsAdapter(getApplicationContext(), accountsList);
        accountsRecyclerView.setAdapter(accountsAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        accountsRecyclerView.setLayoutManager(mLayoutManager);

        if(mockDate) {
            Account a, b, c;
            a = new Account();
            b = new Account();
            c = new Account();
            a.setAccountName("Efectivo");
            a.setAccountBalance(500.70);
            accountsList.add(a);
            b.setAccountName("Debito");
            b.setAccountBalance(7350.51);
            accountsList.add(b);
            c.setAccountName("Plazo fijo");
            c.setAccountBalance(5000);
            accountsList.add(c);
        } else {
            // TODO Real data!!!
        }

        accountsAdapter.notifyDataSetChanged();
    }
}
