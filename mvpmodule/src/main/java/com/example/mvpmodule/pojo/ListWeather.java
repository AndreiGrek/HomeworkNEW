package com.example.mvpmodule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWeather {

    @SerializedName("dt")
    @Expose
    private int dt;

    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    @SerializedName("main")
    @Expose
    private Main main;

    public int getDt() {
        return dt;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }


}
