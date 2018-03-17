package com.casperk.android.umbrellar.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.casperk.android.umbrellar.adapters.ForecastAdapter;
import com.casperk.android.umbrellar.adapters.ForecastAdapterOnClickHandler;
import com.casperk.android.umbrellar.R;
import com.casperk.android.umbrellar.WeatherDetailActivity;
import com.casperk.android.umbrellar.models.City;
import com.casperk.android.umbrellar.models.CityPreference;
import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.models.WeatherForecastForFiveDays;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caspernicus on 10/03/2018.
 */

public class TabForecastOverViewFragment extends Fragment implements ForecastAdapterOnClickHandler {
    private static final String TAG = "TabForecastOverViewFragment";

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;

    private EditText mSearchWeatherForCityEditText;
    private Button mGetWeatherButton;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageTextView;
    private TextView mCityTextView;
    private TextView mWeatherForecastAdviceTextView;
    private ImageView mWeatherIconImageView;
    private String _errorMessage;

    private RequestQueue requestQueue;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_forecast_overview_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_forecast);
        mSearchWeatherForCityEditText = view.findViewById(R.id.search_weather_city_input);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = view.findViewById(R.id.tv_error_message);
        mGetWeatherButton = view.findViewById(R.id.button_getweather);
        mCityTextView = view.findViewById(R.id.tv_overview_city);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);
        mRecyclerView.setAdapter(mForecastAdapter);

        mGetWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWeatherForCityButtonClick(view);
            }
        });

        String preferedCity = new CityPreference(getActivity()).getCity();
        loadWeatherForecastForCity(preferedCity);

        return view;
    }

    public void loadWeatherForecastForCity(String city) {
        setupVolleyAndGson();
        fetchWeatherForecastForFiveDays(city);
    }

    public void handleWeatherForCityButtonClick(View view) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        if (mSearchWeatherForCityEditText.getText().length() == 0) {
            return;
        }

        loadWeatherForecastForCity(mSearchWeatherForCityEditText.getText().toString());
    }

    private void setupVolleyAndGson() {
        requestQueue = Volley.newRequestQueue(getActivity());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        gson = gsonBuilder.create();
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
        @SuppressLint("LongLogTag")
        @Override
        public void onResponse(String response) {
            mErrorMessageTextView.setVisibility(View.GONE);
            mLoadingIndicator.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            final WeatherForecastForFiveDays weatherForecast = gson.fromJson(response, WeatherForecastForFiveDays.class);

            mCityTextView.setText(weatherForecast.getCity().getName().toUpperCase());

            for (WeatherForecast forecast: weatherForecast.getForecastForFiveDays()) {
                forecast.setCity(weatherForecast.getCity());
            }
            mForecastAdapter.setWeatherForecastForFiveDays(weatherForecast.getForecastForFiveDays());

            Log.i(TAG, "Forecast geladen.");
        }
    };

    private final Response.ErrorListener onForecastError = new Response.ErrorListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onErrorResponse(VolleyError error) {
            mLoadingIndicator.setVisibility(View.GONE);
            mErrorMessageTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

            String errorMessage;
            if (error.networkResponse.statusCode == 404)
                errorMessage = "Kon de weersvoorspelling voor de opgegeven stad niet vinden. Probeer het eens in het Engels.";
            else
                errorMessage = "Er is iets fout gegaan. Probeer het later nog eens.";

            mErrorMessageTextView.setText(errorMessage);
            Log.e(TAG, error.toString());
        }
    };

    @Override
    public void onClick(WeatherForecast weatherForOneDay) {
        Context context = getContext();
        Class weatherDetailClass = WeatherDetailActivity.class;
        Intent intentToStartWeatherDetailActivity = new Intent(context, weatherDetailClass);
        intentToStartWeatherDetailActivity.putExtra("WeatherForOneDay_JSON", gson.toJson(weatherForOneDay));
        startActivity(intentToStartWeatherDetailActivity);
    }
}
