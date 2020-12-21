package com.example.mvvmmodule.conventer;

import androidx.room.TypeConverter;

import com.example.mvvmmodule.pojo.Main;
import com.example.mvvmmodule.pojo.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Conventer {
    @TypeConverter
    public String fromMainToString(Main main) {
        return new Gson().toJson(main);
    }

    @TypeConverter
    public Main fromStringToMain(String stringMain) {
        return new Gson().fromJson(stringMain, Main.class);
    }




    @TypeConverter
    public  String listWeatherToString(List<Weather> weathers) {
        return new Gson().toJson(weathers);
    }

//    @TypeConverter
//    public  List<Weather> stringToListWeather(String weathersAsString) {
//        Gson gson = new Gson();
//        ArrayList objects = gson.fromJson(weathersAsString, ArrayList.class);
//        ArrayList<Weather> weathers = new ArrayList<>();
//        for (Object o: objects) {
//            weathers.add(gson.fromJson(o.toString(), Weather.class));
//        }
//        return weathers;
//    }
    @TypeConverter
    public  List<Weather> stringToListWeather(String weathersAsString) {
        Type listType = new TypeToken<List<Weather>>() {}.getType();
        return new Gson().fromJson(weathersAsString, listType);
    }



}
