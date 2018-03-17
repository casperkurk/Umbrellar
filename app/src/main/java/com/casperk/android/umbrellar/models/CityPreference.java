package com.casperk.android.umbrellar.models;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Caspernicus on 16/03/2018.
 */

public class CityPreference {

    private SharedPreferences sharedPreferences;

    public CityPreference(Activity activity){
        sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity(){
        return sharedPreferences.getString("city", "Brussels");
    }

    public void setCity(String city){
        sharedPreferences.edit().putString("city", city).apply();
    }
}
