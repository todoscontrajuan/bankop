package com.me.squad.bankop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.utils.DecimalDigitsInputFilter;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;

public class EditAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.edit_account_tittle));
        }

        final Account account = (Account) getIntent().getSerializableExtra("currentAccount");

        final EditText accountName = (EditText) findViewById(R.id.account_name);
        final EditText newValue = (EditText) findViewById(R.id.new_value_account);
        newValue.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        accountName.setText(account.getAccountName());
        newValue.setText(String.valueOf(account.getAccountBalance()));

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button editAccountButton = (Button) findViewById(R.id.edit_account_button);
        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accountName.getText().toString().matches("") || newValue.getText().toString().matches("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditAccountActivity.this);
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
                    account.setAccountBalance(Double.parseDouble(newValue.getText().toString()));
                    try {
                        final Dao<Account, Integer> accountDao = GeneralUtils.getHelper(getApplicationContext()).getAccountDao();
                        accountDao.update(account);
                        Toast.makeText(EditAccountActivity.this, getString(R.string.edit_account_success_message), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
