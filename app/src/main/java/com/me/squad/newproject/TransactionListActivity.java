package com.me.squad.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.me.squad.newproject.R;

public class TransactionListActivity extends AppCompatActivity {

    private RecyclerView transactionsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
    }
}
