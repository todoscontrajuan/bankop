package com.me.squad.bankop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.me.squad.bankop.utils.DecimalDigitsInputFilter;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_account_title));
        }

        EditText initialValue = (EditText) findViewById(R.id.initial_value_account);
        initialValue.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
