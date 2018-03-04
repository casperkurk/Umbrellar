package com.casperk.android.umbrellar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.casperk.android.umbrellar.models.WeatherCondition;
import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.casperk.android.umbrellar.utilities.WeatherDescriptionMapper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Caspernicus on 4/03/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private ArrayList<WeatherForecast> mWeatherForecastForFiveDays;
    private Context parentContext;

    public ForecastAdapter() {

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

        String iconId = firstWeatherCondition.getIcon();
        String weatherIconUrl = NetworkUtils.getWeatherIconUrl(iconId);
        loadWeatherIconFromUrl(forecastAdapterViewHolder.mWeatherIconImageView, weatherIconUrl);

        forecastAdapterViewHolder.mForecastDateTextView.setText(weatherForecastForThreeHours.getForecastDateAsString());

        String advice = WeatherDescriptionMapper.getDescription(firstWeatherCondition.getId());
        String weatherForecastSummary = firstWeatherCondition.getDescription() + ": " + advice;
        forecastAdapterViewHolder.mForecastSummaryTextView.setText(weatherForecastSummary);
    }

    private void loadWeatherIconFromUrl(ImageView imageView, String url) {
        Picasso.with(parentContext).load(url)
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

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mForecastSummaryTextView;
        public final ImageView mWeatherIconImageView;
        public final TextView mForecastDateTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mForecastSummaryTextView = itemView.findViewById(R.id.weather_forecast_summary);
            mWeatherIconImageView = itemView.findViewById(R.id.weather_forecast_icon);
            mForecastDateTextView = itemView.findViewById(R.id.weather_forecast_date);
        }
    }

    public void setWeatherForecastForFiveDays(ArrayList<WeatherForecast> mWeatherForecastForFiveDays) {
        this.mWeatherForecastForFiveDays = mWeatherForecastForFiveDays;
        notifyDataSetChanged();
    }
}
