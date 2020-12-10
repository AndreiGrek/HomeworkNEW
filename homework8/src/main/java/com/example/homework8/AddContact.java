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

public class AddContact extends AppCompatActivity {
    private String name;
    private String phone;
    private Switch switch1;
    private boolean ifChecked;
    private AppDatabase db;
    private int counter;
    private SharedPreferences sPref;
    private MethodsForDB methodsForDB;
    private int task;
    SettingsActivity settingsActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);


        final EditText editText1 = findViewById(R.id.addName);
        final EditText editText2 = findViewById(R.id.addPhone);
        db = AppDatabase.getInstance(this);

        switch1 = findViewById(R.id.switcher1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editText2.setHint(R.string.email);
                    Toast.makeText(AddContact.this, R.string.modeInputEmail, Toast.LENGTH_SHORT).show();
                } else if (isChecked == false) {
                    editText2.setHint(R.string.phoneNumber);
                    Toast.makeText(AddContact.this, R.string.modeInputPhone, Toast.LENGTH_SHORT).show();
                }
                ifChecked = isChecked;
            }
        });

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                settingsActivity = new SettingsActivity();
                Intent intentSave = new Intent(AddContact.this, MainActivity.class);
                name = editText1.getText().toString();
                phone = editText2.getText().toString();
                loadCounter();

                if (loadTask() == 1 || !sPref.contains("TASK")) {
                    Task1 task1 = new Task1(counter, name, phone, getApplicationContext());
                    task1.handler.sendEmptyMessage(1);
                    task1.startThreadAdd();
                    Toast.makeText(AddContact.this, "Объект добавлен при помощи 1 подхода", Toast.LENGTH_SHORT).show();
                } else if (loadTask() == 2) {
                    Task2 task2 = new Task2(counter, name, phone, getApplicationContext());
                    try {
                        task2.startThreadAdd();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AddContact.this, "Объект добавлен при помощи 2 подхода", Toast.LENGTH_SHORT).show();
                } else if (loadTask() == 3) {
                    Task3 task3 = new Task3(counter, name, phone, getApplicationContext());
                    task3.startThreadAdd();
                    Toast.makeText(AddContact.this, "Объект добавлен при помощи RX Java", Toast.LENGTH_SHORT).show();
                }

                counter++;
                saveCounter(counter);
                startActivity(intentSave);
                finish();
            }
        });
    }

    void saveCounter(int counter) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("COUNTER", counter);
        ed.apply();
    }

    public int loadCounter() {
        sPref = getPreferences(MODE_PRIVATE);
        this.counter = sPref.getInt("COUNTER", 0);
        return counter;
    }

    public int loadTask() {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE);
        this.task = sPref.getInt("TASK", 0);
        return task;
    }
}
