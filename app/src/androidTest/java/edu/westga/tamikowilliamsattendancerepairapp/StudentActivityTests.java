package edu.westga.tamikowilliamsattendancerepairapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import edu.westga.tamikowilliamsattendancerepairapp.UIActivity.StudentActivity;

/**
 * Created by Miko on 4/28/2016.
 */
public class StudentActivityTests extends ActivityInstrumentationTestCase2<StudentActivity> {
    public StudentActivityTests() {
        super(StudentActivity.class);
    }

    public void testActivityExists() {
        StudentActivity activity = getActivity();
        assertNotNull(activity);
    }
    //***************** Testing for add Button and new student *************
    public void testNewStudentLastNameMissingData() {
        StudentActivity activity = getActivity();
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Kelli");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();
        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Kelli" + "", actualText);

    }
    public void testNewStudentFirstNameMissingButLastNamePresentData() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();
            }
        });

        getInstrumentation().sendStringSync("Miller");
        getInstrumentation().waitForIdleSync();
        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();
        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("" + "Miller", actualText);
    }

    public void testNewStudentAdded() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Lonnie");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("Pristone");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();
        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Lonnie" + "Pristone", actualText);
    }

    //************ Testing for find button *********************************
    public void testNewStudentAddedIsFound() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Tandy");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("Millows");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();
        //Tap "Find Button"
        this.buttonTapFind();

        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Tandy" + "Millows", actualText);
    }
    public void testNewStudentFoundMissingLast() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Randy");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        // Tap "Find" button
        // ----------------------
        this.buttonTapFind();

        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Randy" + "", actualText);
    }
    public void testNewStudentFoundMissingFirst() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("Wowers");
        getInstrumentation().waitForIdleSync();

        // Tap "Find" button
        // ----------------------
        this.buttonTapFind();

        // Verify add student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("" + "Wowers", actualText);
    }
    //********************* Testing to remove student **********************************

    public void testNewStudentAddedIsDeleted() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Patrick");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("Pully");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();
        //Tap "Delete Button"
        this.buttonTapDelete();

        // Verify deleted student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Patrick" + "Pully", actualText);
    }
    public void testStudentUnableToDeletedWithMissingData() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("Chubby");
        getInstrumentation().waitForIdleSync();

        //Tap "Delete Button"
        this.buttonTapDelete();

        // Verify deleted student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("" + "Chubby", actualText);
    }
    public void testUnableToLocateNewStudentNotAdded() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Pammy");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("Masson");
        getInstrumentation().waitForIdleSync();

        // Tap "Delete" button
        // ----------------------
        this.buttonTapDelete();

        // Verify student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Pammy" + "Masson", actualText);
    }


    //************************** Testing for Edit Student ********************
    public void testNewStudentAddedIsEdited() {
        StudentActivity activity = getActivity();
        final EditText firstNameText = (EditText) activity.findViewById(R.id.firstEdit);
        final EditText lastNameText = (EditText) activity.findViewById(R.id.lastEdit);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstNameText.requestFocus();

            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Ant");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("Robinson");
        getInstrumentation().waitForIdleSync();

        // Tap "Add" button
        // ----------------------

        this.buttonTapAdd();

        //Tap "Find" button
        this.buttonTapFind();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                firstNameText.requestFocus();

            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("hony");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                lastNameText.requestFocus();

            }
        });
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();
        this.buttonTapEdit();
        //Tap Edit button
        this.buttonTapEdit();

        // Verify deleted student
        // ----------------------

        EditText first = (EditText) activity.findViewById(R.id.firstEdit);
        EditText last = (EditText) activity.findViewById(R.id.lastEdit);
        String actualText = (first.getText().toString() + last.getText().toString());
        assertEquals("Anthony" + "Robinson", actualText);
    }


    //************** Private Helpers *******************************

    //button for testing the add
    private void buttonTapAdd() {
        StudentActivity activity = getActivity();

        Button addButton =
                (Button) activity.findViewById(R.id.newStudentBtn);
        TouchUtils.clickView(this, addButton);
    }
    //button for testing the find
    private void buttonTapFind() {
        StudentActivity activity = getActivity();

        Button findButton =
                (Button) activity.findViewById(R.id.findStudentBtn);
        TouchUtils.clickView(this, findButton);
    }
    //button for testing the edit
    private void buttonTapEdit() {
        StudentActivity activity = getActivity();

        Button editButton =
                (Button) activity.findViewById(R.id.editStudentBtn);
        TouchUtils.clickView(this, editButton);
    }
    //button for testing the delete
    private void buttonTapDelete() {
        StudentActivity activity = getActivity();

        Button deleteButton =
                (Button) activity.findViewById(R.id.deleteStudentBtn);
        TouchUtils.clickView(this, deleteButton);
    }

}
