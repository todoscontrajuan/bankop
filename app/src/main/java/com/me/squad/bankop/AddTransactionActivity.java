package com.me.squad.bankop;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.me.squad.bankop.utils.DecimalDigitsInputFilter;

public class AddTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText transactionDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_transaction_title));
        }

        EditText transactionAmount = (EditText) findViewById(R.id.transaction_amount);
        transactionAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        Spinner transactionTypeSpinner = (Spinner) findViewById(R.id.transaction_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionTypeSpinner.setOnItemSelectedListener(this);
        transactionTypeSpinner.setAdapter(adapter);

        Spinner accountSpinner = (Spinner) findViewById(R.id.account_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.test_accounts, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter1);

        transactionDate = (EditText) findViewById(R.id.transaction_date);
        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
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
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
