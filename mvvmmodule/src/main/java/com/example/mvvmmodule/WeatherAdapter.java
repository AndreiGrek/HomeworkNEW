package com.example.mvvmmodule;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmmodule.pojo.ListWeather;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    List<ListWeather> listWeathers;
    MainActivity mainActivity;
    private String imageUri = "http://openweathermap.org/img/wn/%S@2x.png";
    private String T;

    public WeatherAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<ListWeather> getListWeathers() {
        return listWeathers;
    }

    public void setListWeathers(List<ListWeather> listWeathers, String T) {
        this.listWeathers = listWeathers;
        this.T = T;
        notifyDataSetChanged();
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        if (i < listWeathers.size() - 1) {
            i++;
        }
        ListWeather listWeather = listWeathers.get(i);
        weatherViewHolder.temperatureTextView.setText(String.valueOf(listWeather.getMain().getTemp()) + T);
        weatherViewHolder.textViewDescription.setText(String.valueOf(listWeather.getWeather().get(0).getDescription()));
        weatherViewHolder.textViewTime.setText(listWeather.getDtTxt());
        Glide
                .with(mainActivity)
                .load(String.format(imageUri, listWeather.getWeather().get(0).getIcon()).toLowerCase())
                .into(weatherViewHolder.weatherImageView);
    }

    @Override
    public int getItemCount() {
        return listWeathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView temperatureTextView;
        private TextView textViewDescription;
        private TextView textViewTime;
        private ImageView weatherImageView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            temperatureTextView = itemView.findViewById(R.id.textViewTemperature);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
        }
    }
}
