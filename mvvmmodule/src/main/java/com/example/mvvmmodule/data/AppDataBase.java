package com.example.mvvmmodule.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvmmodule.pojo.ListWeather;

@Database(entities = ListWeather.class, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase dataBase;
    private static String DB_NAME = "mainweather.db";
    private final static Object LOCK = new Object();

    public static AppDataBase getInstance(Context context) {
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, AppDataBase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return dataBase;
        }
    }

    public abstract ListWeatherDao listWeatherDao();
}
