package com.me.squad.newproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.squad.newproject.R;
import com.me.squad.newproject.TransactionDetailsActivity;
import com.me.squad.newproject.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jrodriguez on 7/18/17.
 */

public class TransactionsAdapter extends ArrayAdapter<Transaction> {

    private Context mContext;

    public TransactionsAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.transaction_element, parent, false);
        }

        final Transaction currentTransaction = getItem(position);

        // Transaction type
        CircleImageView imageView = (CircleImageView) listItemView.findViewById(R.id.transaction_type_image);
        setTransactionType(imageView, currentTransaction);

        // Amount
        TextView transactionAmount = (TextView) listItemView.findViewById(R.id.transaction_amount);
        transactionAmount.setText("$" + String.format("%.2f", currentTransaction.getTransactionAmount()));

        // Note
        TextView transactionNote = (TextView) listItemView.findViewById(R.id.transaction_note);
        transactionNote.setText(currentTransaction.getTransactionNote());

        // Date
        Date dateObject = new Date(currentTransaction.getTransactionDateInMilliseconds());
        TextView transactionDate = (TextView) listItemView.findViewById(R.id.transaction_date);
        String formattedTime = formatTime(dateObject);
        transactionDate.setText(formattedTime);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, TransactionDetailsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("currentTransaction", currentTransaction);
                mContext.startActivity(i);
            }
        });

        return listItemView;
    }

    private void setTransactionType(ImageView imageView, Transaction currentTransaction) {
        Drawable color = null;
        Drawable image = null;
        switch (currentTransaction.getTransactionType()) {
            case INCOME:
                color = new ColorDrawable(mContext.getResources().getColor(R.color.income_color));
                image = mContext.getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp);
                break;
            case EXPENSE:
                color = new ColorDrawable(mContext.getResources().getColor(R.color.expense_color));
                image = mContext.getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp);
                break;
            case TRANSFER:
                color = new ColorDrawable(mContext.getResources().getColor(R.color.fab_accounts_color));
                image = mContext.getResources().getDrawable(R.drawable.ic_compare_arrows_black_24dp);
                break;
        }
        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        imageView.setImageDrawable(ld);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return timeFormat.format(dateObject);
    }

}
