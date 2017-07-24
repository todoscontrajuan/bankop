package com.me.squad.newproject.adapter;

import android.content.Context;
import android.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.squad.newproject.R;
import com.me.squad.newproject.model.Account;

import java.util.List;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>{
    private Context mContext;
    private List<Account> accountsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView accountName, accountBalance;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            accountName = (TextView) view.findViewById(R.id.account_name);
            accountBalance = (TextView) view.findViewById(R.id.account_balance);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public AccountsAdapter(Context mContext, List<Account> accountsList) {
        this.mContext = mContext;
        this.accountsList = accountsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Account account = accountsList.get(position);
        holder.accountName.setText(account.getAccountName());
        holder.accountBalance.setText("$" + String.format("%.2f", account.getAccountBalance()));

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_account, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit_account:
                    Toast.makeText(mContext, "Cuenta editada", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_delete_account:
                    Toast.makeText(mContext, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }
}
