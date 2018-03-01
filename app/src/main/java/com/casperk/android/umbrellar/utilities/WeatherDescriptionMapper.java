package com.casperk.android.umbrellar.utilities;

/**
 * Created by Caspernicus on 1/03/2018.
 * zie https://openweathermap.org/weather-conditions voor informatie over de weerdata
 */

public class WeatherDescriptionMapper {
    private static final String NEEM_PARAPLU_MEE = "Het regent. Neem een paraplu mee!";
    private static final String GEEN_PARAPLU_NODIG = "Je hebt geen paraplu nodig.";

    public static String getDescription(int weatherId) {
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
}
