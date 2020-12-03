package com.university.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    protected String ID;
    protected EditText firstNameTextView, lastNameTextView, phoneTextView,
        birthdayDateTextView, emailTextView;
    protected Spinner categorySpinner;
    protected Button saveButton, cancelButton, deleteButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        categorySpinner = findViewById(R.id.categorySpinner);
        birthdayDateTextView = findViewById(R.id.birtdayDateTextView);
        emailTextView = findViewById(R.id.emailTextView);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        deleteButton = findViewById(R.id.deleteButton);

        final Bundle b = getIntent().getExtras();
        if(b != null){

            ID = b.getString("ID");
            firstNameTextView.setText(b.getString("FNAME"));
            lastNameTextView.setText(b.getString("LNAME"));
            phoneTextView.setText(b.getString("PHONE"));
            categorySpinner.getSelectedItem().toString();
            birthdayDateTextView.setText(b.getString("BIRTHDAY"));
            emailTextView.setText(b.getString("EMAIL"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = null;
                try {
                    db = SQLiteDatabase.openOrCreateDatabase(
                            getFilesDir().getPath() + "/phone.db", null);
                    String firstName = firstNameTextView.getText().toString();
                    String lastName = lastNameTextView.getText().toString();
                    String phone = phoneTextView.getText().toString();
                    String spinner = categorySpinner.getSelectedItem().toString();
                    String birthday = birthdayDateTextView.getText().toString();
                    String email = emailTextView.getText().toString();

                    if (firstName.equals("") || phone.equals("")) {
                        firstNameTextView.setHintTextColor(0xFF00FF06);
                        firstNameTextView.setHint("Please, add First Name");
                        phoneTextView.setHintTextColor(0xFFFF0000);
                        phoneTextView.setHint("Please, add Phone number");
                    } else {
                        String s = "UPDATE CONTACTS SET FNAME=?, LNAME=?, PHONE=?, SPINNER=?, BIRTHDAY=?, EMAIL=? WHERE ";
                        s += "ID=?";
                        db.execSQL(s, new Object[]{firstName, lastName, phone, spinner, birthday, email, ID});
                        Toast.makeText(getApplicationContext(), "Contact Updated", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(UpdateActivity.this, MainActivity.class);
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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = null;
                try {
                    db = SQLiteDatabase.openOrCreateDatabase(
                            getFilesDir().getPath() + "/phone.db", null);
                    String s = "DELETE FROM CONTACTS WHERE ";
                    s += "ID=?";
                    db.execSQL(s, new Object[]{ID});
                    Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }finally {
                    if (db != null){
                        db.close();
                    }
                }
                finishActivity(200);
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
            }});}}