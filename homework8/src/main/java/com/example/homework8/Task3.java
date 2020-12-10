package com.example.homework8;

import android.content.Context;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Task3 implements MethodsForDB {

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

    public Task3(int counter, String name, String phone, Context context) {
        this.counter = counter;
        this.name = name;
        this.phone = phone;
        this.context = context;
    }

    public Task3(String name, Context context, int position, String data) {
        this.name = name;
        this.context = context;
        this.position = position;
        this.data = data;
    }

    public void startThreadAdd() {
        Observable.just("one")
                .subscribe(observer);
    }

    public void startThreadEdit() {
        Observable.just("two")
                .subscribe(observer);
    }

    Observer<String> observer = new Observer() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(Object o) {
            if (o == (String) "one") {
                insertDB();
            } else if (o == (String) "two") {
                updateDB();
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    };

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
