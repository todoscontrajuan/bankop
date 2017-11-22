package com.me.squad.bankop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.utils.DatabaseHelper;
import com.me.squad.bankop.utils.DecimalDigitsInputFilter;

import java.sql.SQLException;

public class AddAccountActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_account_title));
        }

        final EditText accountName = (EditText) findViewById(R.id.account_name);

        final EditText initialValue = (EditText) findViewById(R.id.initial_value_account);
        initialValue.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button addAccountButton = (Button) findViewById(R.id.add_account_button);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Account account = new Account();
                if(accountName.getText().toString().matches("") || initialValue.getText().toString().matches("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddAccountActivity.this);
                    alertDialogBuilder.setMessage(getString(R.string.fields_error_message));
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    account.setAccountName(accountName.getText().toString());
                    account.setAccountBalance(Double.parseDouble(initialValue.getText().toString()));
                    try {
                        final Dao<Account, Integer> accountDao = getHelper().getAccountDao();
                        accountDao.create(account);
                        Toast.makeText(AddAccountActivity.this, getString(R.string.accounts_success_message), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
