package com.example.homework7;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey
    public int id;
    private String name;
    private String data;

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public int getId() {
        return id;
    }

//    public Item(String name, String data) {
//        this.name = name;
//        this.data = data;
//    }

    public Item(int id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }
}