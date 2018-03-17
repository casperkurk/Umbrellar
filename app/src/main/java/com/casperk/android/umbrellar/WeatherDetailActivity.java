package com.casperk.android.umbrellar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.casperk.android.umbrellar.models.MainWeatherInfo;
import com.casperk.android.umbrellar.models.WeatherCondition;
import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.utilities.RenderUtils;
import com.casperk.android.umbrellar.utilities.WeatherUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDetailActivity extends AppCompatActivity {

    private static final String TAG = "WeatherDetailActivity";

    TextView mCityTextView;
    TextView mDateTimeTextView;
    TextView mDetailsTextView;
    TextView mTemperatureTextView;
    TextView mAdviceTextView;
    ImageView mWeatherImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        mCityTextView = findViewById(R.id.tv_detail_city);
        mDateTimeTextView = findViewById(R.id.tv_detail_date);
        mDetailsTextView = findViewById(R.id.tv_detail_details);
        mTemperatureTextView = findViewById(R.id.tv_detail_temperature);
        mWeatherImageView = findViewById(R.id.iv_detail_weather_icon);
        mAdviceTextView = findViewById(R.id.tv_detail_advice);

        Intent intentThatStartedThisActivity = getIntent();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        Gson gson = gsonBuilder.create();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("WeatherForOneDay_JSON")) {
                String weatherForOneDay_JSON = intentThatStartedThisActivity.getStringExtra("WeatherForOneDay_JSON");
                WeatherForecast weatherForOneDay = gson.fromJson(weatherForOneDay_JSON, WeatherForecast.class);
                renderWeather(weatherForOneDay);
            }
        }
    }

    private void renderWeather(WeatherForecast weatherForOneDay){
        mCityTextView.setText(weatherForOneDay.getCity().getName().toUpperCase());

        WeatherCondition details = weatherForOneDay.getWeatherConditions().get(0);
        MainWeatherInfo main = weatherForOneDay.getMainWeatherInfo();
        mDetailsTextView.setText(
                details.getDescription().toUpperCase() +
                        "\n" + "Humidity: " + main.getHumidity() + "%" +
                        "\n" + "Pressure: " + main.getPressure() + " hPa");

        mTemperatureTextView.setText(String.format("%s ℃", Math.round(main.getTemp())));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateTime = simpleDateFormat.format(new Date(weatherForOneDay.getForecastDate() * 1000));
        mDateTimeTextView.setText(dateTime);

        mAdviceTextView.setText(WeatherUtils.getAdvice(details.getId()));

        RenderUtils.renderWeatherIconFromResourceId(this, mWeatherImageView, details.getId());
    }


}
