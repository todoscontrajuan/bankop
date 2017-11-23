package com.me.squad.bankop.utils;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jrodriguez on 8/11/17.
 */

public class GeneralUtils {

    // Date format
    public static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
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
}
