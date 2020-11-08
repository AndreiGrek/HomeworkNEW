package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {
EditText fileName;
EditText fileText;
int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        fileName = findViewById(R.id.fileName);
        fileText = findViewById(R.id.fileText);
        fileName.setText(intent.getStringExtra("NAME"));
        fileText.setText(intent.getStringExtra("TEXT"));
        position = intent.getIntExtra("POSITION", 0);

        findViewById(R.id.btnSave2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NAMESAVE", fileName.getText().toString());
                resultIntent.putExtra("TEXTSAVE", fileText.getText().toString());
                resultIntent.putExtra("POSITIONSAVE", position);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}