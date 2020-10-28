package com.example.homework4_2;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements CoordinateListener {
    private Custom custom;
    private SwitchCompat switchCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        custom = findViewById(R.id.customView);
        custom.setCoordinateListener(this);

        switchCompat = findViewById(R.id.swither);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Включен режим Toast", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(custom, "Включен режим Snackbar", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getCoordinates(float x, float y, int color) {
        if (switchCompat.isChecked()) {
            Toast.makeText(this, "Координаты [" + Math.round(x) + ";" + Math.round(y) + "]", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar snackbar = Snackbar.make(custom, "Координаты [" + Math.round(x) + ";" + Math.round(y) + "]", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(color);
            snackbar.show();
        }
    }
}