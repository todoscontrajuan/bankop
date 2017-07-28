package com.me.squad.newproject.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.me.squad.newproject.R;
import com.me.squad.newproject.model.Transaction;

import java.util.List;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    private List<Transaction> transactionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionNote, transactionAmount;

        public MyViewHolder(View view) {
            super(view);
            transactionNote = (TextView) view.findViewById(R.id.note);
            transactionAmount = (TextView) view.findViewById(R.id.amount);
        }
    }

    public TransactionsAdapter(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @Override
    public TransactionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_element, parent, false);

        return new TransactionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TransactionsAdapter.MyViewHolder holder, int position) {
        Transaction transaction = transactionsList.get(position);
        holder.transactionNote.setText(transaction.getTransactionNote());
        holder.transactionAmount.setText(String.valueOf(transaction.getTransactionNote()));
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}
