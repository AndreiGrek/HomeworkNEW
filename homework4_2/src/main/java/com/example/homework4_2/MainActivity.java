package com.example.homework4_2;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String x;
    String y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = String.valueOf( Math.round(event.getX()));
        y = String.valueOf( Math.round(event.getY()));
        Toast.makeText(this, "Координаты ["+x+";"+y+"]", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}