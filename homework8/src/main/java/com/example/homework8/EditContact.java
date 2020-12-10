package com.example.homework8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class EditContact extends AppCompatActivity {
    private Switch switch2;
    private boolean ifChecked;
    private String name;
    private String data;
    private int position;
    private AppDatabase db;
    private SharedPreferences sPref;
    private int task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        final EditText editName = findViewById(R.id.editName);
        final EditText editData = findViewById(R.id.editPhone);
        Intent intentPosition = getIntent();
        position = intentPosition.getIntExtra("POSITION", 0);
        name = intentPosition.getStringExtra("NAME");
        data = intentPosition.getStringExtra("DATA");
        editName.setHint(name);
        editData.setHint(data);
        db = AppDatabase.getInstance(this);

        switch2 = findViewById(R.id.switcher2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editData.setHint(R.string.email);
                    Toast.makeText(EditContact.this, R.string.modeInputEmail, Toast.LENGTH_SHORT).show();
                } else if (isChecked == false) {
                    editData.setHint(R.string.phoneNumber);
                    Toast.makeText(EditContact.this, R.string.modeInputPhone, Toast.LENGTH_SHORT).show();
                }
                ifChecked = isChecked;
            }
        });

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intentSaved = new Intent(EditContact.this, MainActivity.class);
                name = editName.getText().toString();
                data = editData.getText().toString();

                if (loadTask() == 1 || !sPref.contains("TASK")) {
                    Task1 task1 = new Task1(name, getApplicationContext(), position, data);
                    task1.handler.sendEmptyMessage(2);
                    task1.startThreadEdit();
                    Toast.makeText(EditContact.this, "Контакт изменен при помощи 1 подхода", Toast.LENGTH_SHORT).show();
                } else if (loadTask() == 2) {
                    Task2 task2 = new Task2(name, getApplicationContext(), position, data);
                    try {
                        task2.startThreadEdit();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EditContact.this, "Контакт изменен при помощи 2 подхода", Toast.LENGTH_SHORT).show();
                } else if (loadTask() == 3) {
                    Task3 task3 = new Task3(name, getApplicationContext(), position, data);
                    task3.startThreadEdit();
                    Toast.makeText(EditContact.this, "Контакт изменен при помощи 3 подхода", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(EditContact.this, R.string.editContact, Toast.LENGTH_SHORT).show();
                startActivity(intentSaved);
                finish();
            }
        });
    }

    public int loadTask() {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE);
        this.task = sPref.getInt("TASK", 0);
        return task;
    }
}