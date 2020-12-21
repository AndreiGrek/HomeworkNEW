package com.example.mvpmodule;

import com.example.mvpmodule.api.ApiFactory;
import com.example.mvpmodule.api.ApiService;
import com.example.mvpmodule.pojo.MainWeather;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {
    private CompositeDisposable compositeDisposable;
    ListWeatherView view;

    public MainActivityPresenter(ListWeatherView view) {
        this.view = view;
    }

    public void showInfoCelsium() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService
                .getMainWeatherCels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainWeather>() {
                    @Override
                    public void accept(MainWeather mainWeather) {
                        view.showData(mainWeather.getListWeathers(), mainWeather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void showInfoFarenhate() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService
                .getMainWeatherFarenhate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainWeather>() {
                    @Override
                    public void accept(MainWeather mainWeather) {
                        view.showData(mainWeather.getListWeathers(), mainWeather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
