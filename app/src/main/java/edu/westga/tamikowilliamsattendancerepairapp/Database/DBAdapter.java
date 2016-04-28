package edu.westga.tamikowilliamsattendancerepairapp.Database;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import edu.westga.tamikowilliamsattendancerepairapp.Database.DBContract.*;

/**
 * Created by Miko on 4/28/2016.
 */
public class DBAdapter {
    private static final String DB_NAME = "roll_call";

    private static final int DB_VER = 2;
    private static Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBAdapter(Context c) {
        this.context = c;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context c) {
            super(c, DB_NAME, null, DB_VER);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Student.TABLE_NAME + " ("
                    + Student._ID + " INTEGER PRIMARY KEY,"
                    + Student.FIRST_NAME + " TEXT NULL,"
                    + Student.LAST_NAME + " TEXT NULL,"
                    + Student.IS_ACTIVE + " INTEGER NULL"
                    + ");");
            db.execSQL("CREATE TABLE " + Course.TABLE_NAME + " ("
                    + Course._ID + " INTEGER PRIMARY KEY,"
                    + Course.NAME + " TEXT UNIQUE NOT NULL,"
                    + Course.IS_ACTIVE + " INTEGER NOT NULL"
                    + ");");
            db.execSQL("CREATE TABLE " + Enroll.TABLE_NAME + " ("
                    + Enroll._ID + " INTEGER PRIMARY KEY,"
                    + Enroll.STUDENT + " INTEGER NOT NULL,"
                    + Enroll.COURSE + " INTEGER NOT NULL,"
                    + Enroll.ACTIVE + " INTEGER NOT NULL"
                    + ");");
            db.execSQL("CREATE TABLE " + Attendance.TABLE_NAME + " ("
                    + Attendance._ID + " INTEGER PRIMARY KEY,"
                    + Attendance.DATE + " TEXT NOT NULL,"
                    + Attendance.PRESENT + " INTEGER NOT NULL,"
                    + Attendance.ENROLL + " INTEGER NOT NULL,"
                    + Attendance.ACTIVE + " INTEGER NOT NULL"
                    + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Enroll.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Attendance.TABLE_NAME);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addStudent(String firstName, String lastName) {
        Cursor cursor = db.rawQuery("SELECT " + Student._ID + " FROM " + Student.TABLE_NAME +
                        " WHERE " + Student.FIRST_NAME + "=? AND " + Student.LAST_NAME + "=? AND "
                        + Student.IS_ACTIVE + "=1",
                new String[]{firstName, lastName});
        boolean ret = false;
        if (cursor.getCount() == 0) {
            ContentValues args = new ContentValues();
            args.put(Student.FIRST_NAME, firstName);
            args.put(Student.LAST_NAME, lastName);
            args.put(Student.IS_ACTIVE, 1);
            db.insert(Student.TABLE_NAME, null, args);
            ret = true;
        }
        cursor.close();

        if(!ret) {
            cursor = db.rawQuery("SELECT " + Student._ID + " FROM " + Student.TABLE_NAME
                            + " WHERE " + Student.FIRST_NAME + "=? AND " + Student.LAST_NAME
                            + "=? AND " + Student.IS_ACTIVE + "=0",
                    new String[]{firstName, lastName});
            if (cursor.getCount() == 1) {
                ContentValues args = new ContentValues();
                args.put(Student.FIRST_NAME, firstName);
                args.put(Student.LAST_NAME, lastName);
                args.put(Student.IS_ACTIVE, 1);
                db.update(Student.TABLE_NAME, args, Student._ID + "=?",
                        new String[]{String.valueOf(cursor.getLong(0))});
                ret = true;
            }
            cursor.close();
        }
        return ret;
    }

    public String findStudentName(int id) {
        Cursor cursor = db.rawQuery("SELECT " + Student.LAST_NAME + ", " + Student.FIRST_NAME + " FROM " + Student.TABLE_NAME +
                        " WHERE " + Student._ID + "=? AND " + Student.IS_ACTIVE + "=1",
                new String[]{String.valueOf(id)});
        String ret = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ret = cursor.getString(0) + cursor.getString(1);
        }
        cursor.close();
        return ret;
    }

    public int findStudent(String firstName, String lastName) {
        Cursor cursor = db.rawQuery("SELECT " + Student._ID + " FROM " + Student.TABLE_NAME +
                        " WHERE " + Student.FIRST_NAME + "=? AND " + Student.LAST_NAME + "=? AND "
                        + Student.IS_ACTIVE + "=1",
                new String[]{firstName, lastName});
        int ret = -1;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ret = (int) cursor.getInt(0);
        }
        cursor.close();
        return ret;
    }

    public void editStudent(String firstName, String lastName, int id) {
        ContentValues args = new ContentValues();
        args.put(Student.FIRST_NAME, firstName);
        args.put(Student.LAST_NAME, lastName);
        db.update(Student.TABLE_NAME, args, Student._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void removeStudent(int id) {
        ContentValues args = new ContentValues();
        args.put(Student.IS_ACTIVE, 0);
        db.update(Student.TABLE_NAME, args, Student._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean addCourse(String name) {
        Cursor cursor = db.rawQuery("SELECT " + Course._ID + " FROM " + Course.TABLE_NAME +
                        " WHERE " + Course.NAME + "=? ",
                new String[]{name});
        boolean ret = false;
        if (cursor.getCount() == 0) {
            ContentValues args = new ContentValues();
            args.put(Course.NAME, name);
            args.put(Course.IS_ACTIVE, 1);
            db.insert(Course.TABLE_NAME, null, args);
            ret = true;
        }
        cursor.close();

        if(!ret) {
            cursor = db.rawQuery("SELECT " + Course._ID + " FROM " + Course.TABLE_NAME
                            + " WHERE " + Course.NAME + "=? AND " + Course.IS_ACTIVE + "=0",
                    new String[]{name});
            if (cursor.getCount() == 1) {
                ContentValues args = new ContentValues();
                args.put(Course.NAME, name);
                args.put(Course.IS_ACTIVE, 1);
                db.update(Course.TABLE_NAME, args, Course._ID + "=?",
                        new String[]{String.valueOf(cursor.getLong(0))});
                ret = true;
            }
            cursor.close();
        }

        return ret;
    }

    public String getCourseName(int id) {
        Cursor cursor = db.rawQuery("SELECT " + Course.NAME + " FROM " + Course.TABLE_NAME +
                        " WHERE " + Course._ID + "=? AND " + Course.IS_ACTIVE + "=1",
                new String[]{String.valueOf(id)});
        String ret = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ret = cursor.getString(0);
        }
        cursor.close();
        return ret;
    }

    public int getCourseId(String name) {
        Cursor cursor = db.rawQuery("SELECT " + Course._ID + " FROM " + Course.TABLE_NAME +
                        " WHERE " + Course.NAME + "=? AND " + Course.IS_ACTIVE + "=1",
                new String[]{name});
        int ret = -1;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ret = (int) cursor.getInt(0);
        }
        cursor.close();
        return ret;
    }

    public int countStudents(int id) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Enroll.TABLE_NAME +
                        " WHERE " + Enroll.COURSE + "=? AND " + Enroll.ACTIVE + "=1",
                new String[]{String.valueOf(id)});
        int ret = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ret = (int) cursor.getInt(0);
        }
        cursor.close();
        return ret;
    }

    public ArrayList<String> getStudents(int id) {
        Cursor cursor = db.rawQuery("SELECT " + Student.LAST_NAME + ", " + Student.FIRST_NAME
                        + " FROM " + Student.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Student.TABLE_NAME + "." + Student._ID + "=" + Enroll.STUDENT + " AND "
                        + Enroll.COURSE + "=? AND " + Enroll.TABLE_NAME + "." + Enroll.ACTIVE + "=1 AND "
                        + Student.TABLE_NAME + "." + Student.IS_ACTIVE + "=1",
                new String[]{String.valueOf(id)});
        ArrayList<String> ret = null;
        if (cursor.getCount() > 0) {
            ret = new ArrayList<>();
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                ret.add(cursor.getString(0));
                ret.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return ret;
    }

    public void editCourse(String name, int id) {
        ContentValues args = new ContentValues();
        args.put(Course.NAME, name);
        db.update(Course.TABLE_NAME, args, Course._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void removeCourse(int id) {
        ContentValues args = new ContentValues();
        args.put(Course.IS_ACTIVE, 0);
        db.update(Course.TABLE_NAME, args, Course._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean enroll(int student_id, int course_id) {
        boolean ret = false;
        Cursor cursor = db.rawQuery("SELECT " + Enroll._ID + " FROM " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.STUDENT + "=? AND " + Enroll.COURSE + "=? AND "
                        + Enroll.ACTIVE + "=1",
                new String[]{String.valueOf(student_id), String.valueOf(course_id)});
        if (cursor.getCount() == 0) {
            ContentValues args = new ContentValues();
            args.put(Enroll.STUDENT, student_id);
            args.put(Enroll.COURSE, course_id);
            args.put(Enroll.ACTIVE, 1);
            db.insert(Enroll.TABLE_NAME, null, args);
            ret = true;
        }
        cursor.close();

        if(!ret) {
            cursor = db.rawQuery("SELECT " + Enroll._ID + " FROM " + Enroll.TABLE_NAME
                            + " WHERE " + Enroll.STUDENT + "=? AND " + Enroll.COURSE + "=? AND "
                            + Enroll.ACTIVE + "=0",
                    new String[]{String.valueOf(student_id), String.valueOf(course_id)});
            if (cursor.getCount() > 0) {
                ContentValues args = new ContentValues();
                args.put(Enroll.ACTIVE, 1);
                cursor.moveToFirst();
                db.update(Enroll.TABLE_NAME, args, Enroll._ID + "=?",
                        new String[] {String.valueOf(cursor.getLong(0))});
                ret = true;
            }
            cursor.close();
        }
        return ret;
    }

    public int unEnroll(int student_id, int course_id) {
        int ret = 0;
        Cursor cursor = db.rawQuery("SELECT " + Enroll._ID + " FROM " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.STUDENT + "=? AND " + Enroll.COURSE + "=? AND "
                        + Enroll.ACTIVE + "=1",
                new String[]{String.valueOf(student_id), String.valueOf(course_id)});
        if (cursor.getCount() > 0) {
            ContentValues args = new ContentValues();
            args.put(Enroll.ACTIVE, 0);
            cursor.moveToFirst();
            db.update(Enroll.TABLE_NAME, args, Enroll._ID + "=?",
                    new String[]{String.valueOf(cursor.getLong(0))});
            ret = 2;
        }
        cursor.close();

        if(ret == 0) {
            cursor = db.rawQuery("SELECT " + Enroll._ID + " FROM " + Enroll.TABLE_NAME
                            + " WHERE " + Enroll.STUDENT + "=? AND " + Enroll.COURSE + "=? AND "
                            + Enroll.ACTIVE + "=0",
                    new String[]{String.valueOf(student_id), String.valueOf(course_id)});
            if (cursor.getCount() > 0) {
                ret = 1;
            }
            cursor.close();
        }
        return ret;
    }

    public ArrayList<String> getCourses() {
        Cursor cursor = db.rawQuery("SELECT " + Course.NAME
                + " FROM " + Course.TABLE_NAME + " WHERE "
                + Course.IS_ACTIVE + "=1", null);
        ArrayList<String> ret = null;

        if (cursor.getCount() > 0) {
            ret = new ArrayList<>();
            cursor.moveToFirst();
            for(int i=0; i < cursor.getCount(); ++i) {
                ret.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return ret;
    }

    public int getEnrollId(int student_id, int course_id) {
        int ret = -1;

        Cursor cursor = db.rawQuery("SELECT " + Enroll._ID + " FROM " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.STUDENT + "=? AND " + Enroll.COURSE + "=? AND "
                        + Enroll.ACTIVE + "=1",
                new String[]{String.valueOf(student_id), String.valueOf(course_id)});
        if (cursor.getCount() > 0) {
            ContentValues args = new ContentValues();
            args.put(Enroll.ACTIVE, 0);
            cursor.moveToFirst();
            ret = (int)cursor.getLong(0);
        }
        cursor.close();

        return ret;
    }

    public void markAttendance(String date, int present, int enroll) {
        Cursor cursor = db.rawQuery("SELECT " + Attendance._ID + " FROM " + Attendance.TABLE_NAME +
                        " WHERE " + Attendance.ENROLL + "=? AND " + Attendance.DATE + "=? AND "
                        + Attendance.ACTIVE + "=1",
                new String[]{String.valueOf(enroll), date});
        ContentValues args = new ContentValues();
        args.put(Attendance.DATE, date);
        args.put(Attendance.ENROLL, enroll);
        args.put(Attendance.PRESENT, present);
        args.put(Attendance.ACTIVE, 1);
        if (cursor.getCount() == 0) {
            cursor.close();
            cursor = db.rawQuery("SELECT " + Attendance._ID + " FROM " + Attendance.TABLE_NAME +
                            " WHERE " + Attendance.ENROLL + "=? AND " + Attendance.DATE + "=? AND "
                            + Attendance.ACTIVE + "=0",
                    new String[]{String.valueOf(enroll), date});
            if (cursor.getCount() == 0) {
                Log.e("miko", "attendance inserted");
                db.insert(Attendance.TABLE_NAME, null, args);
            } else {
                cursor.moveToFirst();
                Log.e("miko", "Internal Error");
                db.update(Attendance.TABLE_NAME, args, Attendance._ID + "=?",
                        new String[]{String.valueOf(cursor.getLong(0))});
            }
        } else {
            Log.e("miko", "attendance updated");
            cursor.moveToFirst();
            db.update(Attendance.TABLE_NAME, args, Attendance._ID + "=?",
                    new String[]{String.valueOf(cursor.getLong(0))});
        }
        cursor.close();
    }

    public void print() {
        Cursor cursor = db.rawQuery("SELECT *  FROM " + Student.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                Log.e("Miko: Student: ", "ID: " + String.valueOf(cursor.getLong(0))
                        + " F: " + cursor.getString(1)
                        + " L: " + cursor.getString(2)
                        + " " + String.valueOf(cursor.getInt(3)));
                cursor.moveToNext();
            }
        } else Log.e("Miko: Student: ", "No data yet");
        cursor.close();

        cursor = db.rawQuery("SELECT *  FROM " + Course.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                Log.e("Miko: Course: ", "ID: " + String.valueOf(cursor.getLong(0)) + " N: " + cursor.getString(1)
                        + " " + String.valueOf(cursor.getInt(2)));
                cursor.moveToNext();
            }
        } else Log.e("Miko: Course: ", "No data yet");
        cursor.close();

        cursor = db.rawQuery("SELECT *  FROM " + Enroll.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                Log.e("Miko: Enroll: ", "ID: " + String.valueOf(cursor.getLong(0))
                        + " S: " + String.valueOf(cursor.getInt(1))
                        + " C: " + String.valueOf(cursor.getInt(2))
                        + " " + String.valueOf(cursor.getInt(2)));
                cursor.moveToNext();
            }
        } else Log.e("Miko: Enroll: ", "No data yet");
        cursor.close();

        cursor = db.rawQuery("SELECT *  FROM " + Attendance.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                Log.e("Miko: Attendance: ", "ID: " + String.valueOf(cursor.getLong(0))
                        + " D: " + cursor.getString(1)
                        + " P: " + String.valueOf(cursor.getInt(2))
                        + " E: " + String.valueOf(cursor.getInt(3))
                        + " " + String.valueOf(cursor.getInt(4)));
                cursor.moveToNext();
            }
        } else Log.e("Miko: Attendance: ", "No data yet");
        cursor.close();
    }

    public ArrayList<Integer> statsStudent(int id) {
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.STUDENT + "=?",
                new String[]{String.valueOf(id)});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }

    public ArrayList<Integer> statsStudentByMonth(int id, int month) {
        String monthStr;
        if (month < 10) {
            monthStr = "0" + String.valueOf(month);
        } else monthStr = String.valueOf(month);
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.STUDENT + "=? AND "
                        + "strftime('%m', " + Attendance.TABLE_NAME + "." + Attendance.DATE + ") =?",
                new String[]{String.valueOf(id), monthStr});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }

    public ArrayList<Integer> statsStudentByCourse(int id, int course) {
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.STUDENT + "=? AND "
                        + Enroll.TABLE_NAME + "." + Enroll.COURSE + "=?",
                new String[]{String.valueOf(id), String.valueOf(course)});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }

    public ArrayList<Integer> statsStudentByMonthAndCourse(int id, int month, int course) {
        String monthStr;
        if (month < 10) {
            monthStr = "0" + String.valueOf(month);
        } else monthStr = String.valueOf(month);
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.STUDENT + "=? AND "
                        + Enroll.TABLE_NAME + "." + Enroll.COURSE + "=? AND "
                        + "strftime('%m', " + Attendance.TABLE_NAME + "." + Attendance.DATE + ") =?",
                new String[]{String.valueOf(id), String.valueOf(course), monthStr});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }

    public ArrayList<Integer> statsCourse(int id) {
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.COURSE + "=?",
                new String[]{String.valueOf(id)});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }

    public ArrayList<Integer> statsCourseByMonth(int id, int month) {
        String monthStr;
        if (month < 10) {
            monthStr = "0" + String.valueOf(month);
        } else monthStr = String.valueOf(month);
        Cursor cursor = db.rawQuery("SELECT " + Attendance.PRESENT
                        + " FROM " + Attendance.TABLE_NAME + ", " + Enroll.TABLE_NAME
                        + " WHERE " + Enroll.TABLE_NAME + "." + Enroll._ID + "="
                        + Attendance.TABLE_NAME + "." + Attendance.ENROLL
                        + " AND " + Enroll.TABLE_NAME + "." + Enroll.COURSE + "=? AND "
                        + "strftime('%m', " + Attendance.TABLE_NAME + "." + Attendance.DATE + ") =?",
                new String[]{String.valueOf(id), monthStr});
        ArrayList<Integer> ret = new ArrayList<>();
        int presents = 0, absents = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); ++i) {
                if(cursor.getInt(0) == 1) {
                    ++presents;
                } else ++ absents;
                cursor.moveToNext();
            }
        }
        ret.add(presents);
        ret.add(absents);
        cursor.close();
        return ret;
    }
}