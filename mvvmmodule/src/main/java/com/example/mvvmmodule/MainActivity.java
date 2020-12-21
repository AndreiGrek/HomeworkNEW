package com.example.mvvmmodule;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmmodule.pojo.ListWeather;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEmployees;
    private WeatherAdapter adapter;
    private TextView temperatureTextView;
    private TextView descriptionTextView;
    private TextView timeTextView;
    private ImageView imageWeather;
    private String imageUri = "http://openweathermap.org/img/wn/%S@2x.png";
    private double temperature;
    private boolean temperatureCheck;
    private String T = " C";
    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        timeTextView = findViewById(R.id.timeTextView);
        imageWeather = findViewById(R.id.imageWeather);

        recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);
        adapter = new WeatherAdapter(this);
        adapter.setListWeathers(new ArrayList<ListWeather>(), T);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);
        temperatureCheck = getIntent().getBooleanExtra("TEMPCHECK", false);
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        weatherViewModel.getListWeathers().observe(this, new Observer<List<ListWeather>>() {
            @Override
            public void onChanged(List<ListWeather> listWeathers) {
                adapter.setListWeathers(listWeathers, T);
                if (listWeathers.size() != 0) {
                    temperatureTextView.setText(String.valueOf(listWeathers.get(0).getMain().getTemp()) + T);
                    timeTextView.setText(listWeathers.get(0).getDtTxt());
                    descriptionTextView.setText(listWeathers.get(0).getWeather().get(0).getDescription());
                    glide(listWeathers);
                }
            }
        });
        weatherViewModel.getError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null) {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    weatherViewModel.clearErrors();
                }
            }
        });

        if (temperatureCheck) {
            T = " F";
            weatherViewModel.showInfoFarenhate();
        } else {
            T = " C";
            weatherViewModel.showInfoCelsium();
        }

        findViewById(R.id.floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    public TextView getTemperatureTextView() {
        temperatureTextView = findViewById(R.id.temperatureTextView);
        return temperatureTextView;
    }

    public void glide(List<ListWeather> listWeathers) {
        Glide
                .with(MainActivity.this)
                .load(String.format(imageUri, listWeathers.get(0).getWeather().get(0).getIcon()).toLowerCase())
                .into(imageWeather);
    }


}