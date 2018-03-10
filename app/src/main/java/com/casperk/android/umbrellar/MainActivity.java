package com.casperk.android.umbrellar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.casperk.android.umbrellar.models.WeatherForecastForFiveDays;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;

    private EditText mSearchWeatherForCityEditText;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageTextView;
    private TextView mWeatherForecastAdviceTextView;
    private ImageView mWeatherIconImageView;
    private String _errorMessage;

    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_forecast);
        mSearchWeatherForCityEditText = findViewById(R.id.search_weather_city_input);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = findViewById(R.id.error_message);
        /*mWeatherForecastAdviceTextView = findViewById(R.id.weather_forecast_advice);
        mWeatherIconImageView = findViewById(R.id.weather_icon);*/

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter();
        mRecyclerView.setAdapter(mForecastAdapter);
    }

    public void getWeatherForCityButtonClick(View view) {
        if (mSearchWeatherForCityEditText.getText().length() == 0) {
            return;
        }

        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        gson = gsonBuilder.create();

        fetchWeatherForecastForFiveDays(mSearchWeatherForCityEditText.getText().toString());
    }

    private void fetchWeatherForecastForFiveDays(String city) {
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, NetworkUtils.getWeatherForecastForFiveDaysUrl(city), onForecastLoaded, onForecastError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("x-api-key", getString(R.string.open_weather_maps_app_id));

                return params;
            }
        };

        requestQueue.add(request);
    }

    private final Response.Listener<String> onForecastLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            WeatherForecastForFiveDays weatherForecast = gson.fromJson(response, WeatherForecastForFiveDays.class);
            mRecyclerView.setVisibility(View.VISIBLE);
            mForecastAdapter.setWeatherForecastForFiveDays(weatherForecast.getWeatherForecastForecastForFiveDays());

            Log.i("PostActivity", "forecast geladen.");
        }
    };

    private final Response.ErrorListener onForecastError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            Log.e("PostActivity", error.toString());
        }
    };

    /*public class GetWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWeatherIconImageView.setVisibility(View.INVISIBLE);
            mWeatherForecastAdviceTextView.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mErrorMessageTextView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected WeatherForecast doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String city = params[0];
            WeatherForecast weatherForecast;

            try {
                weatherForecast = NetworkUtils.getCurrentWeatherForCity(city, MainActivity.this, mErrorMessageTextView);
            } catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
                _errorMessage = "Kon de ingevoerde stad niet vinden. Probeer het eens in het Engels";
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                _errorMessage = "Oeps, er is iets mis gegaan";
                return null;
            }

            return weatherForecast;
        }

        @Override
        protected void onPostExecute(WeatherForecast weatherForecast) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherForecast == null) {
                mErrorMessageTextView.setVisibility(View.VISIBLE);
                if (_errorMessage != null) {
                    mErrorMessageTextView.setText(_errorMessage);
                } else {
                    _errorMessage = "Oeps, er is iets mis gegaan";
                    mErrorMessageTextView.setText(_errorMessage);
                }

                return;
            }

            String iconId = weatherForecast.getWeatherConditions().get(0).getIcon();
            String weatherIconUrl = NetworkUtils.getWeatherIconUrl(iconId);
            loadWeatherIconFromUrl(weatherIconUrl);
            mWeatherIconImageView.setVisibility(View.VISIBLE);

            int weatherDescriptionId = weatherForecast.getWeatherConditions().get(0).getId();
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
    }*/
}
