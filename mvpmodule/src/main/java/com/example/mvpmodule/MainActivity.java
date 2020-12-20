package com.example.mvpmodule;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvpmodule.pojo.ListWeather;
import com.example.mvpmodule.pojo.MainWeather;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListWeatherView {

    private RecyclerView recyclerViewEmployees;
    private MainActivityPresenter presenter;
    private WeatherAdapter adapter;
    private TextView temperatureTextView;
    private TextView descriptionTextView;
    private TextView timeTextView;
    private ImageView imageWeather;
    private String imageUri = "http://openweathermap.org/img/wn/%S@2x.png";
    private double temperature;
    private boolean temperatureCheck;
    private String T;

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
        presenter = new MainActivityPresenter(this);
        temperatureCheck = getIntent().getBooleanExtra("TEMPCHECK", false);
        if (temperatureCheck){
            presenter.showInfoFarenhate();
        } else {
            presenter.showInfoCelsium();
        }

        findViewById(R.id.floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    public void glide(MainWeather mainWeather) {
        Glide
                .with(MainActivity.this)
                .load(String.format(imageUri, mainWeather.getListWeathers().get(0).getWeather().get(0).getIcon()).toLowerCase())
                .into(imageWeather);
    }

    @Override
    public void showData(List<ListWeather> listWeathers, MainWeather mainWeather) {
        if (!temperatureCheck) {
            T = " C";
            temperature = mainWeather.getListWeathers().get(0).getMain().getTemp();
            temperatureTextView.setText(String.valueOf(temperature) + " C");
            descriptionTextView.setText(mainWeather.getListWeathers().get(0).getWeather().get(0).getDescription());
            timeTextView.setText(mainWeather.getListWeathers().get(0).getDtTxt());
            adapter.setListWeathers(mainWeather.getListWeathers(), T);
            glide(mainWeather);
        } else {
            T = " F";
            temperature = mainWeather.getListWeathers().get(0).getMain().getTemp();
            temperatureTextView.setText(String.valueOf(temperature) + " F");
            descriptionTextView.setText(mainWeather.getListWeathers().get(0).getWeather().get(0).getDescription());
            timeTextView.setText(mainWeather.getListWeathers().get(0).getDtTxt());
            adapter.setListWeathers(mainWeather.getListWeathers(), T);
            glide(mainWeather);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
    }
}