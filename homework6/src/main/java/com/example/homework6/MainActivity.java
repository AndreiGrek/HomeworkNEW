package com.example.homework6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etText;
    private EditText writeFileName;
    private Button btnSave;
    private ArrayList<File> fileArray;
    private Button toSecondActivity;
    private boolean isCheked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.etText);
        writeFileName = findViewById(R.id.writeFileName);
        btnSave = (Button) findViewById(R.id.btnSave);
        fileArray = new ArrayList<>();
        toSecondActivity = findViewById(R.id.toSecondActivity);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheked == false) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                openFileOutput(writeFileName.getText().toString(), MODE_PRIVATE)));
                        bw.write(etText.getText().toString());
                        Toast.makeText(MainActivity.this, "Файл \"" +
                                writeFileName.getText().toString() + "\" успешно сохранен во внутреннюю память", Toast.LENGTH_SHORT).show();
                        fileArray.add(new File(writeFileName.getText().toString()));
                        bw.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (isCheked == true) {
                    File externalPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyFiles");
                    externalPath.mkdir();
                    File externalFile = new File(externalPath, writeFileName.getText().toString());
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(externalFile));
                        bw.write(etText.getText().toString());
                        Toast.makeText(MainActivity.this, "Файл \"" +
                                writeFileName.getText().toString() + "\" успешно сохранен во внешнюю память", Toast.LENGTH_SHORT).show();
                        fileArray.add(new File(writeFileName.getText().toString()));
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        toSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(ArrayList.class.getSimpleName(), fileArray);
                startActivityForResult(intent, 2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2000 && resultCode == Activity.RESULT_OK && data != null) {
            this.fileArray = (ArrayList<File>) data.getExtras().getSerializable(ArrayList.class.getSimpleName());
            this.isCheked = data.getBooleanExtra("ISCHEKED", false);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}