package com.me.squad.newproject.utils;

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
}
