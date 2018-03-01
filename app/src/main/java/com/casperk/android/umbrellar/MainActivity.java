package com.casperk.android.umbrellar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.casperk.android.umbrellar.models.Weather;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.casperk.android.umbrellar.utilities.WeatherDescriptionMapper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchWeatherForCityEditText;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageTextView;
    private TextView mWeatherForecastAdviceTextView;
    private ImageView mWeatherIconImageView;
    private String _errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchWeatherForCityEditText = findViewById(R.id.search_weather_city_input);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = findViewById(R.id.error_message);
        mWeatherForecastAdviceTextView = findViewById(R.id.weather_forecast_advice);
        mWeatherIconImageView = findViewById(R.id.weather_icon);
    }

    public void getWeatherForCityButtonClick(View view) {
        if (mSearchWeatherForCityEditText.getText().length() == 0) {
            return;
        }

        new GetWeatherTask().execute(mSearchWeatherForCityEditText.getText().toString());
    }

    public class GetWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWeatherIconImageView.setVisibility(View.INVISIBLE);
            mWeatherForecastAdviceTextView.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mErrorMessageTextView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Weather doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String city = params[0];
            Weather weather;
            try {
                weather = NetworkUtils.getCurrentWeatherForCity(city, MainActivity.this, mErrorMessageTextView);
            } catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
                _errorMessage = "Kon de ingevoerde stad niet vinden. Probeer het eens in het Engels";
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                _errorMessage = "Oeps, er is iets mis gegaan";
                return null;
            }

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weather == null) {
                mErrorMessageTextView.setVisibility(View.VISIBLE);
                if (_errorMessage != null) {
                    mErrorMessageTextView.setText(_errorMessage);
                } else {
                    _errorMessage = "Oeps, er is iets mis gegaan";
                    mErrorMessageTextView.setText(_errorMessage);
                }

                return;
            }

            String iconId = weather.getWeatherConditions().get(0).getIcon();
            String weatherIconUrl = NetworkUtils.getWeatherIconUrl(iconId);
            loadWeatherIconFromUrl(weatherIconUrl);
            mWeatherIconImageView.setVisibility(View.VISIBLE);

            int weatherDescriptionId = weather.getWeatherConditions().get(0).getId();
            String weatherDescription = WeatherDescriptionMapper.getDescription(weatherDescriptionId);
            mWeatherForecastAdviceTextView.setText(weatherDescription);
            mWeatherForecastAdviceTextView.setVisibility(View.VISIBLE);
        }
    }

    private void loadWeatherIconFromUrl(String url) {
        Picasso.with(this).load(url)
                .error(R.mipmap.ic_launcher)
                .into(mWeatherIconImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
