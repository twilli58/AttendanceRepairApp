package edu.westga.tamikowilliamsattendancerepairapp.Model;

import android.content.Context;
import android.database.SQLException;

import edu.westga.tamikowilliamsattendancerepairapp.Database.DBAdapter;

/**
 * Created by Miko on 4/28/2016.
 */
public class Student {
    String firstName;
    String lastName;


    public Student(Student lastName, Student firstName) {}


    public static boolean addStudent(Context context, String firstName, String lastName) {
        DBAdapter dba = new DBAdapter(context);
        boolean ret = false;
        try {
            dba.open();
            ret = dba.addStudent(firstName, lastName);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static int findStudent(Context context, String firstName, String lastName) {
        DBAdapter dba = new DBAdapter(context);
        int ret = -1;
        try {
            dba.open();
            ret = dba.findStudent(firstName, lastName);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static void editStudent(Context context, String firstName, String lastName, int id) {
        DBAdapter dba = new DBAdapter(context);
        try {
            dba.open();
            dba.editStudent(firstName, lastName, id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeStudent(Context context, int id) {
        DBAdapter dba = new DBAdapter(context);
        try {
            dba.open();
            dba.removeStudent(id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


