package com.example.homework8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sPref;
    private int task = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        findViewById(R.id.radioButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = 1;
                saveTask(task);
                Toast.makeText(SettingsActivity.this,
                        "Выбран режим \"ThreadPoolExecutor + Handler\"", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = 2;
                saveTask(task);
                Toast.makeText(SettingsActivity.this,
                        "Выбран режим \"CompletableFuture + ThreadPoolExecutor\"", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.radioButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = 3;
                saveTask(task);
                Toast.makeText(SettingsActivity.this,
                        "Выбран режим \"RxJava\"", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void saveTask(int task) {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("TASK", task);
        ed.apply();
    }
}