package com.me.squad.bankop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.j256.ormlite.dao.Dao;
import com.me.squad.bankop.AddAccountActivity;
import com.me.squad.bankop.EditAccountActivity;
import com.me.squad.bankop.MainActivity;
import com.me.squad.bankop.R;
import com.me.squad.bankop.TransactionListActivity;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>{
    private Context mContext;
    private List<Account> accountsList;
    private FloatingActionMenu fam;
    private Account account = null;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView accountName, accountBalance, overflow;
        Button seeTransactions;
        CardView accountCard;

        MyViewHolder(View view) {
            super(view);
            accountName = (TextView) view.findViewById(R.id.account_name);
            accountBalance = (TextView) view.findViewById(R.id.account_balance);
            overflow = (TextView) view.findViewById(R.id.overflow);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        account = accountsList.get(position);
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

        holder.accountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
            }
        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = accountsList.get(position);
                PopupMenu popup = new PopupMenu(mContext, holder.overflow);
                popup.inflate(R.menu.menu_account);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit_account:
                                fam.close(true);
                                Intent i = new Intent(mContext, EditAccountActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("currentAccount", account);
                                mContext.startActivity(i);
                                break;
                            case R.id.action_delete_account:
                                fam.close(true);
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                                alertDialogBuilder.setMessage(mContext.getString(R.string.delete_account_warning));
                                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            final Dao<Account, Integer> accountDao = GeneralUtils.getHelper(mContext).getAccountDao();
                                            accountDao.delete(account);
                                            Toast.makeText(mContext, mContext.getString(R.string.delete_account_success_message), Toast.LENGTH_SHORT).show();
                                            dialogInterface.dismiss();
                                            if(mContext instanceof MainActivity){
                                                ((MainActivity)mContext).loadAccountsInformation();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                alertDialogBuilder.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }
}
