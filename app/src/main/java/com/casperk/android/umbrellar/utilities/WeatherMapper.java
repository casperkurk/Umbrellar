package com.casperk.android.umbrellar.utilities;

import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.models.WeatherCondition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caspernicus on 28/02/2018.
 */

public class WeatherMapper {
    public static WeatherForecast mapToWeather(JSONObject weatherData) throws JSONException {
        JSONArray weatherConditionsJson = weatherData.getJSONArray("weather");
        ArrayList<WeatherCondition> weatherConditions = mapToWeatherConditions(weatherConditionsJson);

        return new WeatherForecast(weatherConditions);
    }

    public static ArrayList<WeatherCondition> mapToWeatherConditions(JSONArray weatherConditionsJson) throws JSONException {
        ArrayList<WeatherCondition> weatherConditions = new ArrayList<>();

        for (int i = 0; i < weatherConditionsJson.length(); i++) {
            JSONObject weatherConditionJson = weatherConditionsJson.getJSONObject(i);
            WeatherCondition weatherCondition = new WeatherCondition();
            weatherCondition.setId(weatherConditionJson.getInt(WeatherCondition.ID));
            weatherCondition.setMain(weatherConditionJson.getString(WeatherCondition.MAIN));
            weatherCondition.setDescription(weatherConditionJson.getString(WeatherCondition.DESCRIPTION));
            weatherCondition.setIcon(weatherConditionJson.getString(WeatherCondition.ICON));

            weatherConditions.add(weatherCondition);
        }

        return weatherConditions;
    }
}
