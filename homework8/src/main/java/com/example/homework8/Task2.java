package com.example.homework8;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Task2 implements MethodsForDB {
    private int counter;
    private String name;
    private String phone;
    private Context context;
    private int position;
    private String data;
    Supplier supplier;
    CompletableFuture<Void> future;
    private ThreadPoolExecutor threadPoolExecutor;

    private AppDatabase db;

    public Task2(int counter, String name, String phone, Context context) {
        this.counter = counter;
        this.name = name;
        this.phone = phone;
        this.context = context;
    }

    public Task2(String name, Context context, int position, String data) {
        this.name = name;
        this.context = context;
        this.position = position;
        this.data = data;
    }

    public void startThreadAdd() throws ExecutionException, InterruptedException {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                insertDB();
            }
        }, threadPoolExecutor);
        future.get();
    }

    public void startThreadEdit() throws ExecutionException, InterruptedException {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                updateDB();
            }
        }, threadPoolExecutor);
        future.get();
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
