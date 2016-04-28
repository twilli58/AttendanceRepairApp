package edu.westga.tamikowilliamsattendancerepairapp.Model;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.westga.tamikowilliamsattendancerepairapp.Database.DBAdapter;

/**
 * Created by Miko on 4/28/2016.
 */
public class Attendance {

    public static final String LOG_TAG = "miko";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int markAttendance(Context context, int present, int enrollId) {
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            dba.markAttendance(date, present, enrollId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /* To get local time from UTC time.
     */
    public static String getLocalTime() {
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}