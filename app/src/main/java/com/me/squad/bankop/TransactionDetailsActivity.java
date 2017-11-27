package com.me.squad.bankop;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.me.squad.bankop.model.Account;
import com.me.squad.bankop.model.Transaction;
import com.me.squad.bankop.model.TransactionType;
import com.me.squad.bankop.utils.GeneralUtils;

import java.sql.SQLException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionDetailsActivity extends AppCompatActivity {

    private Dao<Account, Integer> accountDao;
    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Intent i = getIntent();
        transaction = (Transaction) i.getSerializableExtra("currentTransaction");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.transaction_details_title));
        }

        loadTransactionDetails(transaction);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Dao<Transaction, Integer> transactionDao = GeneralUtils.getHelper(this).getTransactionDao();
            final QueryBuilder<Transaction, Integer> queryBuilder = transactionDao.queryBuilder();
            queryBuilder.where().eq("transaction_id", transaction.getTransactionId());
            final PreparedQuery<Transaction> preparedQuery = queryBuilder.prepare();
            for (Transaction transaction : transactionDao.query(preparedQuery)) {
                loadTransactionDetails(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTransactionDetails(final Transaction transaction) {
        // TransactionType and destination account
        CircleImageView imageView = (CircleImageView) findViewById(R.id.transaction_type_image_details);
        Drawable color = null;
        Drawable image = null;
        switch (transaction.getTransactionType()) {
            case INCOME:
                color = new ColorDrawable(this.getResources().getColor(R.color.income_color));
                image = this.getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp);
                break;
            case EXPENSE:
                color = new ColorDrawable(this.getResources().getColor(R.color.expense_color));
                image = this.getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp);
                break;
            case TRANSFER:
                color = new ColorDrawable(this.getResources().getColor(R.color.fab_accounts_color));
                image = this.getResources().getDrawable(R.drawable.ic_compare_arrows_black_24dp);
                RelativeLayout transactionDestinationAccount = (RelativeLayout) findViewById(R.id.account_destination_transaction_details);
                transactionDestinationAccount.setVisibility(View.VISIBLE);
                TextView transactionDestinationAccountText = (TextView) findViewById(R.id.account_destination_transaction_details_value);
                try {
                    accountDao = GeneralUtils.getHelper(this).getAccountDao();
                    final QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
                    queryBuilder.where().eq("account_id", transaction.getTransactionDestinationAccount().getAccountId());
                    final PreparedQuery<Account> preparedQuery = queryBuilder.prepare();
                    for (Account transactionAccount : accountDao.query(preparedQuery)) {
                        transactionDestinationAccountText.setText(transactionAccount.getAccountName());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        imageView.setImageDrawable(ld);

        // Transaction amount
        TextView transactionAmount = (TextView) findViewById(R.id.transaction_amount_details);
        transactionAmount.setText("$" + String.format("%.2f", transaction.getTransactionAmount()));

        // Transaction note
        TextView transactionNote = (TextView) findViewById(R.id.transaction_note_details);
        transactionNote.setText(transaction.getTransactionNote());

        // Transaction date
        Date dateObject = transaction.getTransactionDate();
        TextView transactionDate = (TextView) findViewById(R.id.transaction_date_details);
        String formattedTime = GeneralUtils.formatTime(dateObject);
        transactionDate.setText(formattedTime);

        // Source account
        TextView transactionSourceAccount = (TextView) findViewById(R.id.account_source_transaction_details_value);
        try {
            accountDao = GeneralUtils.getHelper(this).getAccountDao();
            final QueryBuilder<Account, Integer> queryBuilder = accountDao.queryBuilder();
            queryBuilder.where().eq("account_id", transaction.getTransactionSourceAccount().getAccountId());
            final PreparedQuery<Account> preparedQuery = queryBuilder.prepare();
            for (Account transactionAccount : accountDao.query(preparedQuery)) {
                transactionSourceAccount.setText(transactionAccount.getAccountName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Button listeners
        Button editTransaction = (Button) findViewById(R.id.edit_transaction_button);
        editTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
                    Intent i = new Intent(TransactionDetailsActivity.this, EditTransferActivity.class);
                    i.putExtra("currentTransaction", transaction);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TransactionDetailsActivity.this, EditTransactionActivity.class);
                    i.putExtra("currentTransaction", transaction);
                    startActivity(i);
                }
            }
        });

        Button deleteTransaction = (Button) findViewById(R.id.delete_transaction_button);
        deleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Alert dialog for confirmation
            }
        });
    }
}
