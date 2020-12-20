package com.example.mvpmodule.api;




import com.example.mvpmodule.pojo.MainWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("forecast?q=Minsk&units=metric&cnt=25&appid=60ac937a95b7355dbf856446fad8af84")
    Observable<MainWeather> getMainWeatherCels();

    @GET("forecast?q=Minsk&units=imperial&cnt=25&appid=60ac937a95b7355dbf856446fad8af84")
    Observable<MainWeather> getMainWeatherFarenhate();
}
