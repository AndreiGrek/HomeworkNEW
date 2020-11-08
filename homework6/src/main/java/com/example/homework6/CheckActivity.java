package com.example.homework6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class CheckActivity extends AppCompatActivity {
    private CheckBox checkStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        checkStorage = findViewById(R.id.checkStorage);

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra("ISCHEKED", checkStorage.isChecked());
                setResult(Activity.RESULT_OK, backIntent);
                finish();
            }
        });
        checkStorage.isChecked();
    }
}