package com.example.mvvmmodule.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvvmmodule.pojo.ListWeather;

import java.util.List;

@Dao
public interface ListWeatherDao {
    @Query("SELECT * FROM listTable")
    LiveData<List<ListWeather>> getAllWeathers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertWeathers(List<ListWeather> listWeathers);

    @Query("DELETE FROM listTable")
    void deleteAllWeathers();

//    @Query("SELECT 0 FROM listTable")
//    LiveData<List<ListWeather>> getFirstWeather();
}
