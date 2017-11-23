package com.me.squad.bankop;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.me.squad.bankop.adapter.AccountsAdapter;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView accountsRecyclerView;
    private List<Account> accountsList = new ArrayList<>();
    private LinearLayout noAccountsLayout;
    private FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.accounts_title));
        }

        loadUI();
        loadAccountsInformation();

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
                if(accountsList.size() == 0) {
                    showNoAccountsErrorMessage();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddTransactionActivity.class);
                    startActivity(i);
                }
            }
        });

        FloatingActionButton transfersFab = (FloatingActionButton) findViewById(R.id.fab_transfers);
        transfersFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                if(accountsList.size() == 0) {
                    showNoAccountsErrorMessage();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddTransferActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (GeneralUtils.databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            GeneralUtils.databaseHelper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAccountsInformation();
    }

    private void loadUI() {
        noAccountsLayout = (LinearLayout) findViewById(R.id.no_accounts_layout);
        fam = (FloatingActionMenu) findViewById(R.id.fam);
        accountsRecyclerView = (RecyclerView) findViewById(R.id.accounts_recycler_view);
    }

    public void loadAccountsInformation() {
        try {
            Dao<Account, Integer> accountDao = GeneralUtils.getHelper(this).getAccountDao();
            accountsList = accountDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(accountsList.size() == 0) {
            noAccountsLayout.setVisibility(View.VISIBLE);
        } else {
            noAccountsLayout.setVisibility(View.GONE);
        }

        AccountsAdapter accountsAdapter = new AccountsAdapter(MainActivity.this, accountsList, fam);
        accountsRecyclerView.setAdapter(accountsAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        accountsRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void showNoAccountsErrorMessage() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage(getString(R.string.no_accounts_error_message));
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}