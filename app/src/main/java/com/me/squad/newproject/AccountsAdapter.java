package com.me.squad.newproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.squad.newproject.model.Account;

import java.util.List;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>{
    private List<Account> accountList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView accountName, balance;

        public MyViewHolder(View view) {
            super(view);
            accountName = (TextView) view.findViewById(R.id.account_name);
            balance = (TextView) view.findViewById(R.id.balance);
        }
    }

    public AccountsAdapter(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.accountName.setText(account.getAccountName());
        holder.balance.setText(account.getBalance() + "");
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}
