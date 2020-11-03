package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sPref;
    EditText etText;
    EditText writeFileName;
    TextView textView;
    Button btnSave, btnLoad;
    ArrayList<File> fileArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.etText);
        writeFileName = findViewById(R.id.writeFileName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        textView = findViewById(R.id.txtView);
        fileArray = new ArrayList<>();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            openFileOutput(writeFileName.getText().toString(), MODE_PRIVATE)));
                    bw.write(etText.getText().toString());
                    Toast.makeText(MainActivity.this, "Text saved", Toast.LENGTH_SHORT).show();
                    bw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

//        btnLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

//    void saveText() {
//            sPref = getPreferences(MODE_PRIVATE);
//            SharedPreferences.Editor ed = sPref.edit();
//            ed.putString(SAVED_TEXT, etText.getText().toString());
//            ed.commit();
//            Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
//        }
//
//        void loadText() {
//            sPref = getPreferences(MODE_PRIVATE);
//            String savedText = sPref.getString(SAVED_TEXT, "");
//            textView.setText("Сохраненная строка: " + savedText);
//            Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
//    }
}