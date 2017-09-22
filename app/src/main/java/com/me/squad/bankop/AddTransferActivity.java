package com.me.squad.bankop;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.me.squad.bankop.utils.DecimalDigitsInputFilter;

public class AddTransferActivity extends AppCompatActivity {

    private EditText transferDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transfer);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_transfer_title));
        }

        EditText tansferAmount = (EditText) findViewById(R.id.transfer_amount);
        tansferAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        Spinner accountFromSpinner = (Spinner) findViewById(R.id.account_from_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.test_accounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountFromSpinner.setAdapter(adapter);

        Spinner accountToSpinner = (Spinner) findViewById(R.id.account_to_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.test_accounts, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountToSpinner.setAdapter(adapter1);

        transferDate = (EditText) findViewById(R.id.transfer_date);
        transferDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                transferDate.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
