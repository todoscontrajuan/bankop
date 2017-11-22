package com.me.squad.bankop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.me.squad.bankop.adapter.AccountsAdapter;
import com.me.squad.bankop.model.Account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView accountsRecyclerView;
    private List<Account> accountsList = new ArrayList<>();
    private boolean mockDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.accounts_title));
        }

        final FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);

        accountsRecyclerView = (RecyclerView) findViewById(R.id.accounts_recycler_view);
        AccountsAdapter accountsAdapter = new AccountsAdapter(getApplicationContext(), accountsList, fam);
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
            if(accountsList.size() == 0) {
                LinearLayout noAccountsLayout = (LinearLayout) findViewById(R.id.no_accounts_layout);
                noAccountsLayout.setVisibility(View.VISIBLE);
            } else {

            }
        }

        accountsAdapter.notifyDataSetChanged();

        // Listeners
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        FloatingActionButton accountsFab = (FloatingActionButton) findViewById(R.id.fab_accounts);
        accountsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                Intent i = new Intent(getApplicationContext(), AddAccountActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton transactionsFab = (FloatingActionButton) findViewById(R.id.fab_records);
        transactionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                Intent i = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton transfersFab = (FloatingActionButton) findViewById(R.id.fab_transfers);
        transfersFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                Intent i = new Intent(getApplicationContext(), AddTransferActivity.class);
                startActivity(i);
            }
        });
    }
}
