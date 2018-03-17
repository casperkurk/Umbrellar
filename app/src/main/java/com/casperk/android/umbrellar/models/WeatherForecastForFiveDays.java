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
    private ArrayList<WeatherForecast> forecastForFiveDays;

    private City city;

    public String getHttpRequestMessage() {
        return httpRequestMessage;
    }

    public void setHttpRequestMessage(String httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    public ArrayList<WeatherForecast> getForecastForFiveDays() {
        return forecastForFiveDays;
    }

    public void setForecastForFiveDays(ArrayList<WeatherForecast> forecastForFiveDays) {
        this.forecastForFiveDays = forecastForFiveDays;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
