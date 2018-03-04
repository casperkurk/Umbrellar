package com.casperk.android.umbrellar.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Caspernicus on 4/03/2018.
 */

public class WeatherForecastForFiveDays {

    @SerializedName("cod")
    private String httpRequestMessage;

    @SerializedName("list")
    private ArrayList<WeatherForecast> weatherForecastForecastForFiveDays;

    private City city;

    public String getHttpRequestMessage() {
        return httpRequestMessage;
    }

    public void setHttpRequestMessage(String httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    public ArrayList<WeatherForecast> getWeatherForecastForecastForFiveDays() {
        return weatherForecastForecastForFiveDays;
    }

    public void setWeatherForecastForecastForFiveDays(ArrayList<WeatherForecast> weatherForecastForecastForFiveDays) {
        this.weatherForecastForecastForFiveDays = weatherForecastForecastForFiveDays;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
