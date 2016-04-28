package edu.westga.tamikowilliamsattendancerepairapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Miko on 4/28/2016.
 */
public class DBContract {
    public static final class Student implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String FIRST_NAME = "firstname";
        public static final String LAST_NAME = "lastname";
        public static final String IS_ACTIVE = "is_active";
    }

    public static final class Course implements BaseColumns  {
        public static final String TABLE_NAME = "course";
        public static final String NAME = "name";
        public static final String IS_ACTIVE = "is_active";
    }

    public static final class Enroll implements BaseColumns {
        public static final String TABLE_NAME = "enroll";
        public static final String STUDENT = "student_id";
        public static final String COURSE = "course_id";
        public static final String ACTIVE = "is_active";
    }

    public static final class Attendance implements BaseColumns {
        public static final String TABLE_NAME = "attendance";
        public static final String DATE = "date";
        public static final String PRESENT = "is_present";
        public static final String ACTIVE = "is_active";
        public static final String ENROLL = "enroll_id";
    }

}
