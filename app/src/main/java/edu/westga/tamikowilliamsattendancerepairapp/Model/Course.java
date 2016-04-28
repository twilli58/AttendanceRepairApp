package edu.westga.tamikowilliamsattendancerepairapp.Model;

import android.content.Context;
import android.database.SQLException;

import java.util.ArrayList;

import edu.westga.tamikowilliamsattendancerepairapp.Database.DBAdapter;

/**
 * Created by Miko on 4/28/2016.
 */
public class Course {

    public static boolean addCourse(Context context, String name) {
        DBAdapter dba = new DBAdapter(context);
        boolean ret = false;
        try {
            dba.open();
            ret = dba.addCourse(name);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static int findCourse(Context context, String name) {
        DBAdapter dba = new DBAdapter(context);
        int ret = -1;
        try {
            dba.open();
            ret = dba.getCourseId(name);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String findCourseName(Context context, int id) {
        DBAdapter dba = new DBAdapter(context);
        String ret = null;
        try {
            dba.open();
            ret = dba.getCourseName(id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static int countStudents(Context context, int id) {
        DBAdapter dba = new DBAdapter(context);
        int ret = 0;
        try {
            dba.open();
            ret = dba.countStudents(id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<String> getStudents(Context context, int id) {
        DBAdapter dba = new DBAdapter(context);
        ArrayList<String> ret = null;
        try {
            dba.open();
            ret = dba.getStudents(id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void editCourse(Context context, String name, int id) {
        DBAdapter dba = new DBAdapter(context);
        try {
            dba.open();
            dba.editCourse(name, id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCourse(Context context, int id) {
        DBAdapter dba = new DBAdapter(context);
        try {
            dba.open();
            dba.removeCourse(id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addStudentInCourse(Context context, int studentId, int courseId) {
        DBAdapter dba = new DBAdapter(context);
        try {
            dba.open();
            dba.enroll(studentId, courseId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int removeStudentFromCourse(Context context, int studentId, int courseId) {
        DBAdapter dba = new DBAdapter(context);
        int ret = 0;
        try {
            dba.open();
            ret = dba.unEnroll(studentId, courseId);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<String> getCourses(Context context) {
        DBAdapter dba = new DBAdapter(context);
        ArrayList<String> courses = null;
        try {
            dba.open();
            courses = dba.getCourses();
            dba.close();
        } catch(SQLException e) {}
        return courses;
    }

    public static int getEnrollId(Context context, int student_id, int course_id) {
        DBAdapter dba = new DBAdapter(context);
        int ret = -1;
        try {
            dba.open();
            ret = dba.getEnrollId(student_id, course_id);
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
}

