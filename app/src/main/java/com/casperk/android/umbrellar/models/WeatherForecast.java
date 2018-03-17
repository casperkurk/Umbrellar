package com.casperk.android.umbrellar.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Caspernicus on 28/02/2018.
 */

public class WeatherForecast {

    @SerializedName("dt")
    private long forecastDate;

    @SerializedName("dt_txt")
    private String forecastDateAsString;

    @SerializedName("main")
    private MainWeatherInfo mainWeatherInfo;

    @SerializedName("weather")
    private ArrayList<WeatherCondition> weatherConditions;

    private Clouds clouds;

    private Wind wind;

    private City city;

    public WeatherForecast(ArrayList<WeatherCondition> weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public ArrayList<WeatherCondition> getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(ArrayList<WeatherCondition> weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public long getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(long forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getForecastDateAsString() {
        return forecastDateAsString;
    }

    public void setForecastDateAsString(String forecastDateAsString) {
        this.forecastDateAsString = forecastDateAsString;
    }

    public MainWeatherInfo getMainWeatherInfo() {
        return mainWeatherInfo;
    }

    public void setMainWeatherInfo(MainWeatherInfo mainWeatherInfo) {
        this.mainWeatherInfo = mainWeatherInfo;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}