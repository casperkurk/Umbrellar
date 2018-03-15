package com.casperk.android.umbrellar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.models.WeatherForecastForFiveDays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeatherDetailActivity extends AppCompatActivity {

    private static final String TAG = "WeatherDetailActivity";
    private Gson gson;
    private WeatherForecast mWeatherForecast;
   // private TextView mWeatherDetailSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

       // mWeatherDetailSummary = findViewById(R.id.tv_weather_detail_summary);

        Intent intentThatStartedThisActivity = getIntent();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        gson = gsonBuilder.create();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("WeatherForOneDay_JSON")) {
                String weatherForOneDay_JSON = intentThatStartedThisActivity.getStringExtra("WeatherForOneDay_JSON");
                WeatherForecast weatherForOneDay = gson.fromJson(weatherForOneDay_JSON, WeatherForecast.class);
               // mWeatherDetailSummary.setText(weatherForOneDay.getWeatherConditions().get(0).getDescription());
            }
        }
    }
}
