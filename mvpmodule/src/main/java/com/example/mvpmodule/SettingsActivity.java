package com.example.mvpmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Switch aSwitch;
    private boolean temperatureCheck;
    private SharedPreferences  sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.swithcer);

        boolean b = loadBoolean();
        aSwitch.setChecked(loadBoolean());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    temperatureCheck = true;
                    Toast.makeText(SettingsActivity.this, "Включен режим Фаренгейт", Toast.LENGTH_SHORT).show();
                    saveBoolean(temperatureCheck);
                } else {
                    temperatureCheck = false;
                    Toast.makeText(SettingsActivity.this, "Включен режим Цельсий", Toast.LENGTH_SHORT).show();
                    saveBoolean(temperatureCheck);
                }
            }
        });

        findViewById(R.id.toMainActivityButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.putExtra("TEMPCHECK", temperatureCheck);
                startActivity(intent);
                finish();
            }
        });
    }

    void saveBoolean(boolean temperatureCheck) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("ISCHEKED", temperatureCheck);
        ed.apply();
    }

    public boolean loadBoolean() {
        sPref = getPreferences(MODE_PRIVATE);
        this.temperatureCheck = sPref.getBoolean("ISCHEKED", false);
        return temperatureCheck;
    }

}