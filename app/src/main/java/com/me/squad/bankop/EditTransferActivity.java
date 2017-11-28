package com.me.squad.bankop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.model.Transaction;
import com.me.squad.bankop.model.TransactionType;
import com.me.squad.bankop.utils.DecimalDigitsInputFilter;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTransferActivity extends AppCompatActivity {

    private EditText transferDate;
    private Dao<Account, Integer> accountDao;
    private Calendar calendar;
    private List<Account> accountsList;
    private String oldSourceAccountName;
    private String oldDestinationAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transfer);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.edit_transfer_title));
        }

        Intent i = getIntent();
        final Transaction transaction = (Transaction) i.getSerializableExtra("currentTransaction");

        final EditText transferAmount = (EditText) findViewById(R.id.transfer_amount);
        transferAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
        transferAmount.setText(String.valueOf(transaction.getTransactionAmount()));

        List<String> accountNames = getAccountsInformation();

        final Spinner sourceAccountSpinner = (Spinner) findViewById(R.id.account_from_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get the order of the account in the list to set the spinner
        int order = 0;
        for (int j = 0; j < accountsList.size(); j++) {
            if (transaction.getTransactionSourceAccount().getAccountId() == accountsList.get(j).getAccountId()) {
                order = j;
                oldSourceAccountName = accountsList.get(j).getAccountName();
            }
        }
        sourceAccountSpinner.setAdapter(adapter);
        sourceAccountSpinner.setSelection(order);

        final Spinner destinationAccountSpinner = (Spinner) findViewById(R.id.account_to_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get the order of the account in the list to set the spinner
        order = 0;
        for (int j = 0; j < accountsList.size(); j++) {
            if (transaction.getTransactionDestinationAccount().getAccountId() == accountsList.get(j).getAccountId()) {
                order = j;
                oldDestinationAccountName = accountsList.get(j).getAccountName();
            }
        }
        destinationAccountSpinner.setAdapter(adapter1);
        destinationAccountSpinner.setSelection(order);

        transferDate = (EditText) findViewById(R.id.transfer_date);
        calendar = Calendar.getInstance();
        transferDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = GeneralUtils.prepareDatePickerDialog(transferDate, calendar);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        Date defaultDate = transaction.getTransactionDate();
        transferDate.setText(GeneralUtils.formatTime(defaultDate));
        calendar.setTime(defaultDate);

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final EditText transferNote = (EditText) findViewById(R.id.transfer_note);
        transferNote.setText(transaction.getTransactionNote());

        Button editTransferButton = (Button) findViewById(R.id.edit_transfer_button);
        editTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transferAmount.getText().toString().matches("") || transferDate.getText().toString().matches("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTransferActivity.this);
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
                    if(sourceAccountSpinner.getSelectedItemPosition() == destinationAccountSpinner.getSelectedItemPosition()) {
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTransferActivity.this);
                        alertDialogBuilder.setMessage(getString(R.string.same_account_error));
                        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        transaction.setTransactionType(TransactionType.TRANSFER);
                        transaction.setTransactionAmount(Double.parseDouble(transferAmount.getText().toString()));
                        transaction.setTransactionDate(calendar.getTime());
                        transaction.setTransactionNote(transferNote.getText().toString());
                        try {
                            final Dao<Transaction, Integer> transactionDao = GeneralUtils.getHelper(getApplicationContext()).getTransactionDao();

                            // Account source
                            final QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
                            queryBuilder.where().eq("account_name", sourceAccountSpinner.getSelectedItem().toString());
                            final PreparedQuery<Account> preparedQuery = queryBuilder.prepare();
                            for (Account transactionAccount : accountDao.query(preparedQuery)) {
                                transaction.setTransactionSourceAccount(transactionAccount);
                            }

                            // Account Destination
                            final QueryBuilder<Account, Integer> queryBuilder1 = accountDao.queryBuilder();
                            queryBuilder1.where().eq("account_name", destinationAccountSpinner.getSelectedItem().toString());
                            final PreparedQuery<Account> preparedQuery1 = queryBuilder1.prepare();
                            for (Account transactionAccount : accountDao.query(preparedQuery1)) {
                                transaction.setTransactionDestinationAccount(transactionAccount);
                            }

                            transactionDao.update(transaction);
                            updateAccountInformation(sourceAccountSpinner.getSelectedItem().toString(),
                                    destinationAccountSpinner.getSelectedItem().toString(),
                                    transaction);
                            Toast.makeText(EditTransferActivity.this, getString(R.string.transfers_success_message), Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private List<String> getAccountsInformation() {
        List<String> accountNames = new ArrayList<>();
        try {
            accountDao = GeneralUtils.getHelper(this).getAccountDao();
            accountsList = accountDao.queryForAll();
            for (int i = 0; i < accountsList.size(); i++) {
                accountNames.add(accountsList.get(i).getAccountName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountNames;
    }

    private void updateAccountInformation(String sourceAccountName, String destinationAccountName, Transaction transaction) {
        try {
            accountDao = GeneralUtils.getHelper(this).getAccountDao();
            // We should update every balance individually so we don't miss any change

            // Source account
            final QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
            queryBuilder.where().eq("account_name", sourceAccountName);
            final PreparedQuery<Account> preparedQuery = queryBuilder.prepare();
            for (Account transactionAccount : accountDao.query(preparedQuery)) {
                transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() - transaction.getTransactionAmount());
                accountDao.update(transactionAccount);
            }

            // Old source account
            final QueryBuilder<Account, Integer> queryBuilder2 = accountDao.queryBuilder();
            queryBuilder2.where().eq("account_name", oldSourceAccountName);
            final PreparedQuery<Account> preparedQuery2 = queryBuilder2.prepare();
            for (Account transactionAccount : accountDao.query(preparedQuery2)) {
                transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() + transaction.getTransactionAmount());
                accountDao.update(transactionAccount);
            }

            // Destination account
            final QueryBuilder<Account, Integer> queryBuilder1 = accountDao.queryBuilder();
            queryBuilder1.where().eq("account_name", destinationAccountName);
            final PreparedQuery<Account> preparedQuery1 = queryBuilder1.prepare();
            for (Account transactionAccount : accountDao.query(preparedQuery1)) {
                transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() + transaction.getTransactionAmount());
                accountDao.update(transactionAccount);
            }

            // Old destination account
            final QueryBuilder<Account, Integer> queryBuilder3 = accountDao.queryBuilder();
            queryBuilder3.where().eq("account_name", oldDestinationAccountName);
            final PreparedQuery<Account> preparedQuery3 = queryBuilder3.prepare();
            for (Account transactionAccount : accountDao.query(preparedQuery3)) {
                transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() - transaction.getTransactionAmount());
                accountDao.update(transactionAccount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (GeneralUtils.databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            GeneralUtils.databaseHelper = null;
        }
    }
}