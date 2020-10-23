package com.example.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {

    private static final String RESULT1 = "RESULT1";
    private static final String RESULT2 = "RESULT2";
    private static final String RESULT3 = "RESULT3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final HashSet<Integer> hashSet = (HashSet<Integer>) getIntent().getExtras().getSerializable(HashSet.class.getSimpleName());
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Переданный набор чисел: " +  hashSet.toString());


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentResult = new Intent();
                final int summ = summHashSet(hashSet);
                final int med = medianaHashSet(hashSet);
                final int calc = calculateHashSet(hashSet);

                intentResult.putExtra("RESULT1", summ);
                intentResult.putExtra("RESULT2", med);
                intentResult.putExtra("RESULT3", calc);
                setResult(Activity.RESULT_OK, intentResult);
                finish();
            }
        });
    }


    private int summHashSet(HashSet<Integer> hashSet) {
        int counter = 0;
        for (Integer i : hashSet) {
            counter = counter + i;
        }
        return counter;
    }

    private int medianaHashSet(HashSet<Integer> hashSet) {
        int mediana = 0;
        int counter = 0;
        for (Integer i : hashSet) {
            counter = counter + i;
        }
        mediana = counter / 2;
        return mediana;
    }

    private int calculateHashSet(HashSet<Integer> hashSet) {
        Integer[] array = hashSet.toArray(new Integer[hashSet.size()]);
        int counter = 0;
        int diff = 0;
        for (int i = 0; i < (array.length / 2); i++) {
            counter = counter + array[i];
        }
        for (int i = array.length / 2; i < array.length; i++) {
            diff = diff - array[i];
        }
        return counter / diff;
    }

}
