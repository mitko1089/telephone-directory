package com.university.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class AddActivity extends AppCompatActivity {

    protected TextView firstNameTextView;
    protected TextView lastNameTextView;
    protected TextView phoneTextView;
    protected TextView birthdayTextView;
    protected TextView emailTextView;
    protected Button saveButton;
    protected Button cancelButton;
    protected Spinner categorySpinner;
    protected String ID;

    protected void initDB() throws SQLException {
        SQLiteDatabase db = null;
        db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/phone.db", null);

        String q = "CREATE TABLE IF NOT EXISTS CONTACTS( ";
        q += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        q += "FNAME TEXT NOT NULL, ";
        q += "LNAME TEXT, ";
        q += "PHONE TEXT NOT NULL, ";
        q += "SPINNER TEXT, ";
        q += "BIRTHDAY TEXT, ";
        q += "EMAIL TEXT, ";
        q += "UNIQUE(FNAME, PHONE)); ";
        db.execSQL(q);
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        birthdayTextView = findViewById(R.id.birthdayDateTextView);
        emailTextView = findViewById(R.id.emailTextView);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        categorySpinner = findViewById(R.id.categorySpinner);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            ID = b.getString("ID");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = null;
                try {
                    initDB();
                    db = SQLiteDatabase.openOrCreateDatabase(
                            getFilesDir().getPath() + "/phone.db", null);
                    String FNAME = firstNameTextView.getText().toString();
                    String LNAME = lastNameTextView.getText().toString();
                    String PHONE = phoneTextView.getText().toString();
                    String SPINNER = categorySpinner.getSelectedItem().toString();
                    String BIRTHDAY = birthdayTextView.getText().toString();
                    String EMAIL = emailTextView.getText().toString();

                    if (FNAME.equals("") || PHONE.equals("")) {
                        firstNameTextView.setHintTextColor(0xFF00FF06);
                        firstNameTextView.setHint("Please, add First Name");
                        phoneTextView.setHintTextColor(0xFF00FF06);
                        phoneTextView.setHint("Please, add Phone number");
                    } else {
                        String s = "INSERT INTO CONTACTS(FNAME, LNAME, PHONE, SPINNER, BIRTHDAY, EMAIL) ";
                        s += "VALUES(?, ?, ?, ?, ?, ?); ";
                        db.execSQL(s, new Object[]{FNAME, LNAME, PHONE, SPINNER, BIRTHDAY, EMAIL});
                        Toast.makeText(getApplicationContext(), "Contact created", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    if (db != null) {
                        db.close();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
