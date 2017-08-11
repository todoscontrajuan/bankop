package com.me.squad.newproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.me.squad.newproject.model.Account;
import com.me.squad.newproject.model.Transaction;
import com.me.squad.newproject.utils.GeneralUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Intent i = getIntent();
        Transaction transaction = (Transaction) i.getSerializableExtra("currentTransaction");

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
                TextView transactionDestinationAccount = (TextView) findViewById(R.id.account_destination_transaction_details);
                transactionDestinationAccount.setVisibility(View.VISIBLE);
                transactionDestinationAccount.setText(
                        transactionDestinationAccount.getText() + ": " +
                                transaction.getTransactionDestinationAccount().getAccountName());
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
        Date dateObject = new Date(transaction.getTransactionDateInMilliseconds());
        TextView transactionDate = (TextView) findViewById(R.id.transaction_date_details);
        String formattedTime = GeneralUtils.formatTime(dateObject);
        transactionDate.setText(formattedTime);

        // Origin account
        TextView transactionSourceAccount = (TextView) findViewById(R.id.account_source_transaction_details);
        transactionSourceAccount.setText(
                transactionSourceAccount.getText() + ": " +
                transaction.getTransactionSourceAccount().getAccountName());

        // Button listeners
        Button editTransaction = (Button) findViewById(R.id.edit_transaction_button);
        editTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Edit transaction activity
            }
        });

        Button deleteTransaction = (Button) findViewById(R.id.delete_transaction_button);
        deleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Alert dialong for confirmation
            }
        });
    }
}
