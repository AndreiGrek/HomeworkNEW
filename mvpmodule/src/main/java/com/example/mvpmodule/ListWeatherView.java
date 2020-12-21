package com.example.mvpmodule;

import com.example.mvpmodule.pojo.ListWeather;
import com.example.mvpmodule.pojo.MainWeather;

import java.util.List;

public interface ListWeatherView {
    void showData(List<ListWeather> listWeathers, MainWeather mainWeather);
    void showError();
}
