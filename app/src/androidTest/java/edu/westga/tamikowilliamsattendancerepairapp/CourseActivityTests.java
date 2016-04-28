package edu.westga.tamikowilliamsattendancerepairapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import edu.westga.tamikowilliamsattendancerepairapp.UIActivity.CourseActivity;

/**
 * Created by Miko on 4/28/2016.
 */
public class CourseActivityTests extends ActivityInstrumentationTestCase2<CourseActivity> {

    public CourseActivityTests() {
        super(CourseActivity.class);
    }

    public void testActivityExists() {
        CourseActivity activity = getActivity();
        assertNotNull(activity);
    }

    //**************** Test New Course *****************************
    public void testNewCourseAdded() {
        CourseActivity activity = getActivity();
        final EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                course.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Culinary Arts");
        getInstrumentation().waitForIdleSync();

        this.buttonCTapAdd();
        // Verify Course is there
        // ----------------------

        EditText courseText = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (courseText.getText().toString());
        assertEquals("Culinary Arts", actualText);

    }

    public void testCourseAddWithMissingData() {
        CourseActivity activity = getActivity();
        final EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                course.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonCTapAdd();

        // Verify course
        // ----------------------

        EditText courseText = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (courseText.getText().toString());
        assertEquals("", actualText);

    }
    //**************** Test find Course *****************************
    public void testNewCourseAddedIsFound() {
        CourseActivity activity = getActivity();
        final EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                course.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Automotive");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonCTapAdd();
        //Tap "Find Button"
        this.buttonCTapFind();

        // Verify course
        // ----------------------

        EditText courseText = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (courseText.getText().toString());
        assertEquals("Automotive", actualText);
    }
    public void testCourseFoundWithMissingData() {
        CourseActivity activity = getActivity();
        final EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                course.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonCTapFind();

        // Verify course
        // ----------------------

        EditText courseText = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (courseText.getText().toString());
        assertEquals("", actualText);
    }



    //**************** Test remove Course *****************************

    public void testNewCourseAddedIsRemoved() {
        CourseActivity activity = getActivity();
        final EditText courseNameText = (EditText) activity.findViewById(R.id.courseNameEdit);


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                courseNameText.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("Party Events");
        getInstrumentation().waitForIdleSync();

        this.buttonCTapAdd();
        this.buttonCTapFind();
        this.buttonCTapDelete();
        // Verify Course is Removed
        // ----------------------

        EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (course.getText().toString());

        assertEquals("Party Events", actualText);
    }

    public void testCourseDeletedWithMissingData() {
        CourseActivity activity = getActivity();
        final EditText course = (EditText) activity.findViewById(R.id.courseNameEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                course.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonCTapDelete();

        // Verify course
        // ----------------------

        EditText courseText = (EditText) activity.findViewById(R.id.courseNameEdit);
        String actualText = (courseText.getText().toString());
        assertEquals("", actualText);
    }
    //************** Private Helpers *******************************

    //button for testing the add
    private void buttonCTapAdd() {
        CourseActivity activity = getActivity();

        Button addButton =
                (Button) activity.findViewById(R.id.newCourseBtn);
        TouchUtils.clickView(this, addButton);
    }
    //button for testing the find
    private void buttonCTapFind() {
        CourseActivity activity = getActivity();

        Button findButton =
                (Button) activity.findViewById(R.id.findCourseBtn);
        TouchUtils.clickView(this, findButton);
    }

    //button for testing the delete
    private void buttonCTapDelete() {
        CourseActivity activity = getActivity();

        Button deleteButton =
                (Button) activity.findViewById(R.id.deleteCourseBtn);
        TouchUtils.clickView(this, deleteButton);
    }
}
