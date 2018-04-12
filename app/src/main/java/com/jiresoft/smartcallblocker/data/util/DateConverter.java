package com.jiresoft.smartcallblocker.data.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


/**
 * Created by victorariasgonzalez on 2/21/18.
 */

public class DateConverter {

    public static String DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss a";

    public static String dateToString(long dateLong) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date = new Date(dateLong);
        String dateFormatted = dateFormat.format(date);
        return dateFormatted;
    }
}
