package com.me.squad.bankop.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.me.squad.bankop.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jrodriguez on 8/11/17.
 */

public class GeneralUtils {

    // Device language
    public static String systemLanguage;

    // Date format
    public static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (systemLanguage != null) {
            if (systemLanguage.equals("es")) {
                timeFormat = new SimpleDateFormat("dd/MM/yyyy");
            }
        }
        return timeFormat.format(dateObject);
    }

    // DB Util
    public static DatabaseHelper databaseHelper = null;
    public static DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public static DatePickerFragment prepareDatePickerDialog(final EditText transactionDate, final Calendar calendar) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate;
                if (systemLanguage.equals("es")) {
                    selectedDate = day + "/" + (month+1) + "/" + year;
                } else {
                    selectedDate = (month+1) + "/" + day + "/" + year;
                }
                transactionDate.setText(selectedDate);
                calendar.set(year, month, day);
            }
        });
        return newFragment;
    }

    // Valid amount checking
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
