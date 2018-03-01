package com.casperk.android.umbrellar.models;

import java.util.ArrayList;

/**
 * Created by Caspernicus on 28/02/2018.
 */

public class Weather {
    private ArrayList<WeatherCondition> weatherConditions;

    public Weather(ArrayList<WeatherCondition> weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public ArrayList<WeatherCondition> getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(ArrayList<WeatherCondition> weatherConditions) {
        this.weatherConditions = weatherConditions;
    }
}