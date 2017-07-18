package com.me.squad.newproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.me.squad.newproject.R;
import com.me.squad.newproject.model.Transaction;

import java.util.ArrayList;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class TransactionsAdapter extends ArrayAdapter<Transaction> {

    public TransactionsAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.transaction_element, parent, false);
        }

        Transaction currentTransaction = getItem(position);
        TextView noteTextView = (TextView) listItemView.findViewById(R.id.note);
        noteTextView.setText(currentTransaction.getNote());
        TextView amountTextView = (TextView) listItemView.findViewById(R.id.amount);
        amountTextView.setText(String.valueOf(currentTransaction.getAmount()));

        return listItemView;
    }
}
