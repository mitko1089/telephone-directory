package com.university.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected Button addContactButton;
    protected ListView contactsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactButton = findViewById(R.id.addContactButton);
        contactsListView = findViewById(R.id.contactsListView);

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/phone.db", null);
            String q = "SELECT * FROM CONTACTS ORDER BY FNAME; ";
            Cursor c = db.rawQuery(q, null);
            final ArrayList<String> arrayList = new ArrayList<String>();
            while (c.moveToNext()) {
                String FNAME = c.getString(c.getColumnIndex("FNAME"));
                String LNAME = c.getString(c.getColumnIndex("LNAME"));
                String PHONE = c.getString(c.getColumnIndex("PHONE"));
                String SPINNER = c.getString(c.getColumnIndex("SPINNER"));
                String BIRTHDAY = c.getString(c.getColumnIndex("BIRTHDAY"));
                String EMAIL = c.getString(c.getColumnIndex("EMAIL"));
                String ID = c.getString(c.getColumnIndex("ID"));

                arrayList.add(ID + "\t" + FNAME + "\t" + LNAME + "\t" + PHONE
                        + "\t" + SPINNER + "\t" + BIRTHDAY + "\t" + EMAIL + "\t\n ");
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_selectable_list_item, arrayList);

            contactsListView.setAdapter(arrayAdapter);
            db.close();

            contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = "";
                    String clickedText = arrayList.get(position);
                    selected = clickedText;

                    String[] elements = selected.split("\t");
                    String ID = elements[0];

                    Intent intent = new Intent(MainActivity.this, ContactActivity.class);

                    Bundle b = new Bundle();
                    b.putString("ID", ID);
                    b.putString("FNAME", elements[1]);
                    b.putString("LNAME", elements[2]);
                    b.putString("PHONE", elements[3]);
                    b.putString("SPINNER", elements[4]);
                    b.putString("BIRTHDAY", elements[5]);
                    b.putString("EMAIL", elements[6]);

                    intent.putExtras(b);
                    startActivityForResult(intent, 200, b);
                }
            });
        } catch (Exception e) {

        } finally {
            if (db == null) {
                db.close();
            }
        }

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
    }
}
