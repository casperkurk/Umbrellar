package com.casperk.android.umbrellar.utilities;

import android.content.Context;
import android.widget.TextView;

import com.casperk.android.umbrellar.R;
import com.casperk.android.umbrellar.models.WeatherForecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Caspernicus on 28/02/2018.
 */

public class NetworkUtils {
    private static final String OPEN_WEATHER_MAP_5_DAYS_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=%s,BE&units=metric";
    private static final String OPEN_WEATHER_MAP_CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s,BE&units=metric";
    private static final String OPEN_WEATHER_MAP_WEATHER_ICON_URL = "http://openweathermap.org/img/w/%s.png";


    public static String getWeatherForecastForFiveDaysUrl(String city) {
        return String.format(OPEN_WEATHER_MAP_5_DAYS_FORECAST_URL, city);
    }

    public static WeatherForecast getCurrentWeatherForCity(String city, Context context, TextView errorMessageTextView) throws IOException, JSONException {
        URL weatherRequestUrl = new URL(getCurrentWeatherForCityUrl(city));

        HttpURLConnection connection = (HttpURLConnection) weatherRequestUrl.openConnection();
        connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

        JSONObject weatherData = getWeatherDataJson(connection);

        int httpStatusCode = weatherData.getInt("cod");
        if(httpStatusCode != 200){
            return null;
        }

        WeatherForecast weatherForecast = WeatherMapper.mapToWeather(weatherData);

        return weatherForecast;
    }

    private static String getCurrentWeatherForCityUrl(String city) {
        return String.format(OPEN_WEATHER_MAP_CURRENT_WEATHER_URL, city);
    }

    private static JSONObject getWeatherDataJson(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuffer json = new StringBuffer(1024);
        String tmp="";
        while((tmp = reader.readLine()) != null)
            json.append(tmp).append("\n");

        reader.close();

        return new JSONObject(json.toString());
    }

    private static String getFiveDaysForecastForCityUrl(String city) {
        return String.format(OPEN_WEATHER_MAP_5_DAYS_FORECAST_URL, city);
    }

    public static String getWeatherIconUrl(String iconId) {
        return String.format(OPEN_WEATHER_MAP_WEATHER_ICON_URL, iconId);
    }
}
