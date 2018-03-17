package com.casperk.android.umbrellar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.casperk.android.umbrellar.R;
import com.casperk.android.umbrellar.models.WeatherCondition;
import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.utilities.RenderUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Caspernicus on 4/03/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private List<WeatherForecast> mWeatherForecastForFiveDays;
    private Context parentContext;
    private final ForecastAdapterOnClickHandler mClickHandler;

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {

        mClickHandler = clickHandler;
    }

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        int layoutIdForForecastItem = R.layout.weather_forecast_item;
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForForecastItem, parent, shouldAttachToParentImmediately);

        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        WeatherForecast weatherForecastForThreeHours = mWeatherForecastForFiveDays.get(position);
        WeatherCondition firstWeatherCondition = weatherForecastForThreeHours.getWeatherConditions().get(0);

        RenderUtils.renderWeatherIconFromResourceId(parentContext, forecastAdapterViewHolder.mWeatherIconImageView, firstWeatherCondition.getId());

        /*SimpleDateFormat simpleDateFormat_Date = new SimpleDateFormat("d");
        String date = simpleDateFormat_Date.format(new Date(weatherForecastForThreeHours.getForecastDate() * 1000));*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(weatherForecastForThreeHours.getForecastDate() * 1000));
        int bleh = Calendar.DAY_OF_WEEK;
        String dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        SimpleDateFormat simpleDateFormat_Time = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat_Time.format(new Date(weatherForecastForThreeHours.getForecastDate() * 1000));

        forecastAdapterViewHolder.mForecastTimeTextView.setText(time);
        forecastAdapterViewHolder.mForecastDateTextView.setText(dayOfWeek);
        forecastAdapterViewHolder.mForecastTemperatureTextView.setText(String.format("%s ℃", Math.round(weatherForecastForThreeHours.getMainWeatherInfo().getTemp())));
    }

    private String getDayOfWeek(int day) {
        String dayAsString = "";
        switch (day) {
            case 1:
                dayAsString = "Zondag";
                break;
            case 2:
                dayAsString = "Maandag";
                break;
            case 3:
                dayAsString = "Dinsdag";
                break;
            case 4:
                dayAsString = "Woensdag";
                break;
            case 5:
                dayAsString = "Donderdag";
                break;
            case 6:
                dayAsString = "Vrijdag";
                break;
            case 7:
                dayAsString = "Zaterdag";
                break;
        }
        return dayAsString;
    }

    @Override
    public int getItemCount() {
        if (mWeatherForecastForFiveDays == null) return 0;

        return mWeatherForecastForFiveDays.size();
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mWeatherIconImageView;
        public final TextView mForecastTimeTextView;
        public final TextView mForecastDateTextView;
        public final TextView mForecastTemperatureTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherIconImageView = itemView.findViewById(R.id.weather_forecast_icon);
            mForecastTimeTextView = itemView.findViewById(R.id.tv_weather_forecast_time);
            mForecastDateTextView = itemView.findViewById(R.id.tv_weather_forecast_date);
            mForecastTemperatureTextView = itemView.findViewById(R.id.tv_weather_forecast_temp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            WeatherForecast weatherForOneDay = mWeatherForecastForFiveDays.get(adapterPosition);
            mClickHandler.onClick(weatherForOneDay);
        }
    }

    public void setWeatherForecastForFiveDays(ArrayList<WeatherForecast> mWeatherForecastForFiveDays) {
        this.mWeatherForecastForFiveDays = mWeatherForecastForFiveDays;
        notifyDataSetChanged();
    }
}
