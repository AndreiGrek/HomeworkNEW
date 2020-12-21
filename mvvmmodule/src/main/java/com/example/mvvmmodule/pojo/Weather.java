package com.example.mvvmmodule.pojo;

import androidx.room.TypeConverters;

import com.example.mvvmmodule.conventer.Conventer;
import com.example.mvvmmodule.conventer.Converter2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@TypeConverters(value = Converter2.class)

public class Weather {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}