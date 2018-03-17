package com.casperk.android.umbrellar.utilities;

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

    private static String getCurrentWeatherForCityUrl(String city) {
        return String.format(OPEN_WEATHER_MAP_CURRENT_WEATHER_URL, city);
    }

    public static String getWeatherIconUrl(String iconId) {
        return String.format(OPEN_WEATHER_MAP_WEATHER_ICON_URL, iconId);
    }
}
