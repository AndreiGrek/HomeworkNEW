package com.example.homework8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Task1 implements MethodsForDB, Runnable {
    private int counter;
    private String name;
    private String phone;
    private Context context;
    private int position;
    private String data;
    private ThreadPoolExecutor threadPoolExecutor;
    private AppDatabase db;

    public Task1(String name, Context context, int position, String data) {
        this.name = name;
        this.context = context;
        this.position = position;
        this.data = data;
    }

    public Task1(int counter, String name, String phone, Context context) {
        this.counter = counter;
        this.name = name;
        this.phone = phone;
        this.context = context;
    }

    public void startThreadAdd() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        threadPoolExecutor.submit(new Task1(counter, name, phone, context));
        threadPoolExecutor.shutdown();
    }

    public void startThreadEdit() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        threadPoolExecutor.submit(new Task1(name, context, position, data));
        threadPoolExecutor.shutdown();
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                insertDB();
            } else if (msg.what == 2) {
                updateDB();
            }
        }
    };

    @Override
    public void run() {
    }

    @Override
    public void insertDB() {
        db = AppDatabase.getInstance(context);
        db.contactDao().insert(new Item(counter, name, phone));
    }

    @Override
    public void updateDB() {
        db = AppDatabase.getInstance(context);
        db.contactDao().update(new Item(position, name, data));
    }
}
