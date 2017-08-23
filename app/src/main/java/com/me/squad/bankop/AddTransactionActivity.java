package com.me.squad.bankop;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_transaction_title));
        }

        Spinner spinner = (Spinner) findViewById(R.id.transaction_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
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

    }
}
