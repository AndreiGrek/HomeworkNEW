package com.example.homework6;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ArrayAdapter<File> mAdapter;
    private ArrayList<File> fileArray;
    private String text;
    private ListView lvMain;
    private boolean isCheked;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fileArray = (ArrayList<File>) getIntent().getExtras().getSerializable(ArrayList.class.getSimpleName());
        mAdapter = new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, fileArray);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(mAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SecondActivity.this, EditItem.class);
                intent.putExtra("NAME", fileArray.get(position).toString());
                intent.putExtra("POSITION", position);

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileArray.get(position).toString())));
                    text = br.readLine();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("TEXT", text);
                startActivityForResult(intent, 1000);
            }
        });

        findViewById(R.id.toMainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainIntent = new Intent();
                toMainIntent.putExtra(ArrayList.class.getSimpleName(), fileArray);
                toMainIntent.putExtra("ISCHEKED", isCheked);
                setResult(Activity.RESULT_OK, toMainIntent);
                finish();
            }
        });

        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSettingsIntent = new Intent(SecondActivity.this, CheckActivity.class);
                startActivityForResult(toSettingsIntent, 3000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            if (isCheked == false) {
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                            (openFileOutput(data.getStringExtra("NAMESAVE"), MODE_PRIVATE)));
                    bw.write(data.getStringExtra("TEXTSAVE"));
                    Toast.makeText(SecondActivity.this, "Файл \"" +
                            data.getStringExtra("NAMESAVE") + "\" успешно изменен", Toast.LENGTH_SHORT).show();
                    fileArray.set(data.getIntExtra("POSITIONSAVE", 0), new File(data.getStringExtra("NAMESAVE")));
                    bw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (isCheked == true) {
                File externalPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyFiles");
                externalPath.mkdir();
                File externalFile = new File(externalPath, data.getStringExtra("NAMESAVE"));
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(externalFile));
                    bw.write(data.getStringExtra("TEXTSAVE"));
                    Toast.makeText(SecondActivity.this, "Файл \"" +
                            data.getStringExtra("NAMESAVE") + "\" успешно изменен", Toast.LENGTH_SHORT).show();
                    fileArray.set(data.getIntExtra("POSITIONSAVE", 0), new File(data.getStringExtra("NAMESAVE")));
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 3000 && resultCode == Activity.RESULT_OK && data != null) {
            isCheked = data.getBooleanExtra("ISCHEKED", false);
        }
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(mAdapter);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
