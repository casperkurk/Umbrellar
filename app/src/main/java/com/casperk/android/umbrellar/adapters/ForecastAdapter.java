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
import com.casperk.android.umbrellar.utilities.WeatherMapper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

        int weatherIconResourceId = WeatherMapper.getWeatherIconResourceId(firstWeatherCondition.getId());
        loadWeatherIconFromResourceId(forecastAdapterViewHolder.mWeatherIconImageView, weatherIconResourceId);

        String[] splittedDateTime = weatherForecastForThreeHours.getForecastDateAsString().split(" ");
        String time = splittedDateTime[1].substring(0, splittedDateTime[1].length() - 3);

        forecastAdapterViewHolder.mForecastTimeTextView.setText(time);
        forecastAdapterViewHolder.mForecastDateTextView.setText(splittedDateTime[0]);
    }

    private void loadWeatherIconFromResourceId(ImageView imageView, int weatherIconResourceId) {
        Picasso.with(parentContext).load(weatherIconResourceId)
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

    @Override
    public int getItemCount() {
        if (mWeatherForecastForFiveDays == null) return 0;

        return mWeatherForecastForFiveDays.size();
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mWeatherIconImageView;
        public final TextView mForecastTimeTextView;
        public final TextView mForecastDateTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherIconImageView = itemView.findViewById(R.id.weather_forecast_icon);
            mForecastTimeTextView = itemView.findViewById(R.id.tv_weather_forecast_time);
            mForecastDateTextView = itemView.findViewById(R.id.tv_weather_forecast_date);
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
