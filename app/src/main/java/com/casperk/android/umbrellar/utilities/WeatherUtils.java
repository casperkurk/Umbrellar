package com.casperk.android.umbrellar.utilities;

import com.casperk.android.umbrellar.R;

/**
 * Created by Caspernicus on 1/03/2018.
 * zie https://openweathermap.org/weather-conditions voor informatie over de weerdata
 */

public class WeatherUtils {
    public static final String NEEM_PARAPLU_MEE = "Het gaat regenen. Vergeet je paraplu niet!";
    public static final String GEEN_PARAPLU_NODIG = "Je hebt geen paraplu nodig.";

    public static String getAdvice(int weatherId) {
        switch (weatherId) {
            case 200 :
            case 201 :
            case 202 :
            case 230 :
            case 231 :
            case 232 :
            case 300 :
            case 301 :
            case 302 :
            case 310 :
            case 311 :
            case 312 :
            case 313 :
            case 314 :
            case 321 :
            case 500 :
            case 501 :
            case 502 :
            case 503 :
            case 504 :
            case 511 :
            case 520 :
            case 521 :
            case 522 :
            case 531 : return NEEM_PARAPLU_MEE;
            default : return GEEN_PARAPLU_NODIG;
        }
    }

    public static boolean mustBringUmbrella(int weatherId) {
        return getAdvice(weatherId).equals(NEEM_PARAPLU_MEE);
    }

    public static int getWeatherIconResourceId(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.storm;
        }
        if (weatherId >= 300 && weatherId <= 531) {
            return R.drawable.umbrella;
        }
        if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snow;
        }
        if (weatherId >= 701 && weatherId <= 781) {
            return R.drawable.mist;
        }
        if (weatherId == 800) {
            return R.drawable.sunny;
        }

        return R.drawable.cloudy;
    }
}
