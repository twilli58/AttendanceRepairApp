package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Student;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class StudentActivity extends AppCompatActivity {
    TextView idView;
    EditText lastNameView;
    EditText firstNameView;
    TextView studentIdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        String[] studentArray = {"charlie", "Betty", "Larry"};

        lastNameView = (EditText) findViewById(R.id.lastEdit);
        firstNameView = (EditText) findViewById(R.id.firstEdit);
        studentIdView = (TextView) findViewById(R.id.studentIdVal);

        ArrayAdapter<String> myAdapter = new
                ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                studentArray);

    }

    public void newStudent (View view) {
        String lastName = lastNameView.getText().toString();
        String firstName = firstNameView.getText().toString();

        if(lastName.length() == 0) {
            lastNameView.setError("Last Name cannot be empty");
            return;
        }

        if(firstNameView.length() == 0) {
            firstNameView.setError("First Name cannot be empty");
            return;
        }

        if(Student.addStudent(this, firstName.toLowerCase(), lastName.toLowerCase())) {
            Toast.makeText(this, "Student added.", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Student already present.", Toast.LENGTH_SHORT).show();
    }

    public void findStudent (View view) {
        String lastName = lastNameView.getText().toString();
        String firstName = firstNameView.getText().toString();

        if(lastName.length() == 0) {
            lastNameView.setError("Last Name cannot be empty");
            return;
        }

        if(firstNameView.length() == 0) {
            firstNameView.setError("First Name cannot be empty");
            return;
        }

        int id = Student.findStudent(this, firstName.toLowerCase(), lastName.toLowerCase());

        if(id == -1) {
            Toast.makeText(this, "No student found.", Toast.LENGTH_SHORT).show();
        } else {
            studentIdView.setText(String.valueOf(id));
        }
    }

    public void editStudent (View view) {
        String lastName = lastNameView.getText().toString();
        String firstName = firstNameView.getText().toString();
        String idText = studentIdView.getText().toString();

        if(lastName.length() == 0) {
            lastNameView.setError("Last Name cannot be empty");
            return;
        }

        if(firstNameView.length() == 0) {
            firstNameView.setError("First Name cannot be empty");
            return;
        }

        if(idText.equalsIgnoreCase("not assigned")) {
            Toast.makeText(this, "No ID found to edit.", Toast.LENGTH_SHORT).show();
            return;
        }

        Student.editStudent(this, firstName.toLowerCase(), lastName.toLowerCase(), Integer.parseInt(idText));
        Toast.makeText(this, "Student with ID " + idText + " updated.", Toast.LENGTH_SHORT).show();

    }
    public void removeStudent (View view) {
        String lastName = lastNameView.getText().toString();
        String firstName = firstNameView.getText().toString();

        if(lastName.length() == 0) {
            lastNameView.setError("Last Name cannot be empty");
            return;
        }

        if(firstNameView.length() == 0) {
            firstNameView.setError("First Name cannot be empty");
            return;
        }

        int id = Student.findStudent(this, firstName.toLowerCase(), lastName.toLowerCase());

        if(id == -1) {
            Toast.makeText(this, "No student found.", Toast.LENGTH_SHORT).show();
        } else {
            Student.removeStudent(this, id);
            Toast.makeText(this, "Student with ID " + String.valueOf(id) + " deleted.", Toast.LENGTH_SHORT).show();
        }
    }

}
