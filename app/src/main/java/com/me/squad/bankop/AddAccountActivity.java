package com.me.squad.bankop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_account_title));
        }
    }
}
