package com.me.squad.bankop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.widget.Button;
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

import com.github.clans.fab.FloatingActionMenu;
import com.me.squad.bankop.R;
import com.me.squad.bankop.TransactionListActivity;
import com.me.squad.bankop.model.Account;

import java.util.List;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>{
    private Context mContext;
    private List<Account> accountsList;
    private FloatingActionMenu fam;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView accountName, accountBalance;
        ImageView overflow;
        Button seeTransactions;
        CardView accountCard;

        MyViewHolder(View view) {
            super(view);
            accountName = (TextView) view.findViewById(R.id.account_name);
            accountBalance = (TextView) view.findViewById(R.id.account_balance);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            seeTransactions = (Button) itemView.findViewById(R.id.see_transactions);
            accountCard = (CardView) itemView.findViewById(R.id.account_card_view);
        }
    }

    public AccountsAdapter(Context mContext, List<Account> accountsList, FloatingActionMenu fam) {
        this.mContext = mContext;
        this.accountsList = accountsList;
        this.fam = fam;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Account account = accountsList.get(position);
        holder.accountName.setText(account.getAccountName());
        holder.accountBalance.setText("$" + String.format("%.2f", account.getAccountBalance()));

        holder.seeTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                Intent i = new Intent(mContext, TransactionListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("currentAccount", account);
                mContext.startActivity(i);
            }
        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                showPopupMenu(holder.overflow);
            }
        });

        holder.accountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
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
    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        MyMenuItemClickListener() {
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
