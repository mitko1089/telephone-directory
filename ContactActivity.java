package com.university.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    protected TextView firstNameTextView;
    protected TextView lastNameTextView;
    protected TextView phoneTextView;
    protected TextView birthdayDateTextView;
    protected TextView emailTextView;
    protected TextView spinnerTextView;

    protected Button editButton;
    protected Button backButton;

    protected String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        spinnerTextView = findViewById(R.id.spinnerTextView);
        birthdayDateTextView = findViewById(R.id.birthdayDateTextView);
        emailTextView = findViewById(R.id.emailTextView);
        editButton = findViewById(R.id.editButton);
        backButton = findViewById(R.id.backButton);

        Bundle b = getIntent().getExtras();
        if(b != null){
            ID = b.getString("ID");
            firstNameTextView.setText(b.getString("FNAME"));
            lastNameTextView.setText(b.getString("LNAME"));
            phoneTextView.setText(b.getString("PHONE"));
            spinnerTextView.setText(b.getString("SPINNER"));
            birthdayDateTextView.setText(b.getString("BIRTHDAY"));
            emailTextView.setText(b.getString("EMAIL"));
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = firstNameTextView.getText().toString();
                String lastName = lastNameTextView.getText().toString();
                String phone = phoneTextView.getText().toString();
                String categorySpinner = spinnerTextView.getText().toString();
                String birthday= birthdayDateTextView.getText().toString();
                String email = emailTextView.getText().toString();

                Intent i = new Intent(ContactActivity.this, UpdateActivity.class);

                Intent other = getIntent();
                other.getExtras().getString("firstName", firstName);
                other.getExtras().getString("lastName", lastName);
                other.getExtras().getString("phone", phone);
                other.getExtras().getString("spinner", categorySpinner);
                other.getExtras().getString("birthday", birthday);
                other.getExtras().getString("email", email);

                i.putExtras(getIntent());
                startActivity(i);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ContactActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
