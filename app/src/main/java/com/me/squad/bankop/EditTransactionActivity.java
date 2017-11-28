package com.me.squad.bankop;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

public class EditTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText transactionDate;
    private Calendar calendar;
    private Dao<Account, Integer> accountDao;
    private List<Account> accountsList = null;
    private double oldAmount = 0;
    private String oldAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        Intent i = getIntent();
        final Transaction transaction = (Transaction) i.getSerializableExtra("currentTransaction");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.edit_transaction_title));
        }

        final EditText transactionAmount = (EditText) findViewById(R.id.transaction_amount);
        transactionAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
        oldAmount = transaction.getTransactionAmount();
        transactionAmount.setText(String.valueOf(oldAmount));

        final Spinner transactionTypeSpinner = (Spinner) findViewById(R.id.transaction_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionTypeSpinner.setOnItemSelectedListener(this);
        transactionTypeSpinner.setAdapter(adapter);
        transactionTypeSpinner.setSelection(transaction.getTransactionType().ordinal());

        List<String> accountNames = getAccountsInformation();

        final Spinner accountSpinner = (Spinner) findViewById(R.id.account_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int order = 0;
        for (int j = 0; j < accountsList.size(); j++) {
            if (transaction.getTransactionSourceAccount().getAccountId() == accountsList.get(j).getAccountId()) {
                order = j;
                oldAccountName = accountsList.get(j).getAccountName();
            }
        }
        accountSpinner.setAdapter(adapter1);
        accountSpinner.setSelection(order);

        transactionDate = (EditText) findViewById(R.id.transaction_date);
        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        Date defaultDate = transaction.getTransactionDate();
        transactionDate.setText(GeneralUtils.formatTime(defaultDate));
        calendar = Calendar.getInstance();
        calendar.setTime(defaultDate);

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final EditText transactionNote = (EditText) findViewById(R.id.transaction_note);
        transactionNote.setText(transaction.getTransactionNote());

        Button editTransactionButton = (Button) findViewById(R.id.edit_transaction_button);
        editTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transactionAmount.getText().toString().matches("") || transactionDate.getText().toString().matches("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTransactionActivity.this);
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
                    if(TransactionType.EXPENSE.ordinal() == transactionTypeSpinner.getSelectedItemPosition()) {
                        transaction.setTransactionType(TransactionType.EXPENSE);
                    } else {
                        transaction.setTransactionType(TransactionType.INCOME);
                    }
                    transaction.setTransactionAmount(Double.parseDouble(transactionAmount.getText().toString()));
                    transaction.setTransactionDate(calendar.getTime());
                    transaction.setTransactionNote(transactionNote.getText().toString());
                    try {
                        final Dao<Transaction, Integer> transactionDao = GeneralUtils.getHelper(getApplicationContext()).getTransactionDao();
                        final QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
                        queryBuilder.where().eq("account_name", accountSpinner.getSelectedItem().toString());
                        final PreparedQuery<Account> preparedQuery = queryBuilder.prepare();
                        for (Account transactionAccount : accountDao.query(preparedQuery)) {
                            transaction.setTransactionSourceAccount(transactionAccount);
                        }
                        transactionDao.update(transaction);
                        updateAccountInformation(accountSpinner.getSelectedItem().toString(), transaction);
                        Toast.makeText(EditTransactionActivity.this, getString(R.string.transactions_success_message), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String[] transactionsTypeArray = getResources().getStringArray(R.array.transaction_types_array);
        if(parent.getItemAtPosition(pos).equals(transactionsTypeArray[0])) {
            if(getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                        ContextCompat.getColor(this,R.color.expense_color)));
            }
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.expense_color_darker));
        } else {
            if(getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                        ContextCompat.getColor(this,R.color.income_color)));
            }
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.income_color_darker));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Nothing
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                transactionDate.setText(selectedDate);

                calendar = Calendar.getInstance();
                calendar.set(year, month, day);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
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

    private void updateAccountInformation(String accountName, Transaction transaction) {
        PreparedQuery<Account> preparedQuery = null;
        try {
            accountDao = GeneralUtils.getHelper(this).getAccountDao();
            QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
            queryBuilder.where().eq("account_name", accountName);
            preparedQuery = queryBuilder.prepare();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(oldAccountName.equals(accountName)) {
            try {
                for (Account transactionAccount : accountDao.query(preparedQuery)) {
                    if(transaction.getTransactionType() == TransactionType.EXPENSE) {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() - transaction.getTransactionAmount() + oldAmount);
                    } else {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() + transaction.getTransactionAmount() - oldAmount);
                    }
                    accountDao.update(transactionAccount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (Account transactionAccount : accountDao.query(preparedQuery)) {
                    if(transaction.getTransactionType() == TransactionType.EXPENSE) {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() - transaction.getTransactionAmount());
                    } else {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() + transaction.getTransactionAmount());
                    }
                    accountDao.update(transactionAccount);
                }
                QueryBuilder<Account, Integer> queryBuilder1 = accountDao.queryBuilder();
                queryBuilder1.where().eq("account_name", oldAccountName);
                PreparedQuery<Account> preparedQuery1 = queryBuilder1.prepare();
                for (Account transactionAccount : accountDao.query(preparedQuery1)) {
                    if(transaction.getTransactionType() == TransactionType.EXPENSE) {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() + transaction.getTransactionAmount());
                    } else {
                        transactionAccount.setAccountBalance(transactionAccount.getAccountBalance() - transaction.getTransactionAmount());
                    }
                    accountDao.update(transactionAccount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
