package com.casperk.android.umbrellar.utilities;

import android.content.Context;
import android.widget.ImageView;

import com.casperk.android.umbrellar.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Caspernicus on 16/03/2018.
 */

public class RenderUtils {

    public static void renderWeatherIconFromResourceId(Context context, ImageView imageView, int weatherId) {

        Picasso.with(context).load(getWeatherIconResourceId(weatherId))
                .error(R.mipmap.ic_launcher)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private static int getWeatherIconResourceId(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.umbrella;
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
