package com.example.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String KEY1 = "KEY1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        int hashSetSize = random.nextInt(10);

        final HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i<hashSetSize; i++){
            hashSet.add(random.nextInt(10));
        }


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(HashSet.class.getSimpleName(), hashSet);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        TextView textView = findViewById(R.id.resultView);
        if (requestCode==1000 && resultCode == Activity.RESULT_OK && data != null){
            int summ = data.getExtras().getInt("RESULT1");
            int med = data.getExtras().getInt("RESULT2");
            int calc = data.getExtras().getInt("RESULT3");
            textView.setText(String.valueOf("Сумма чисел массива: " + summ + "\n" +
                    "Среднее арифметическое массива " + med + " \n" +
                    "Калькуляция: " + calc));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}