package edu.westga.tamikowilliamsattendancerepairapp.Model;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static ArrayList<Integer> getStudentStats(Context context, int studentId) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsStudent(studentId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Integer> getStudentStatsByCourse(Context context, int studentId, int courseId) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsStudentByCourse(studentId, courseId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Integer> getStudentStatsByCourseAndMonth(Context context, int studentId, int courseId, int month) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsStudentByMonthAndCourse(studentId, month, courseId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Integer> getStudentStatsByMonth(Context context, int studentId, int month) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsStudentByMonth(studentId, month);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Integer> getCourseStats(Context context, int courseId) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsCourse(courseId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Integer> getCourseStatsByMonth(Context context, int month, int courseId) {
        ArrayList<Integer> ret = null;
        DBAdapter dba = new DBAdapter(context);
        try {
            String date = getLocalTime();
            Log.e("miko", date);
            dba.open();
            ret = dba.statsCourseByMonth(courseId, month);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }

}



