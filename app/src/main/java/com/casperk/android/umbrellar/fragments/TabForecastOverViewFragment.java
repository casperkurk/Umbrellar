package com.casperk.android.umbrellar.fragments;

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
import com.casperk.android.umbrellar.ForecastAdapter;
import com.casperk.android.umbrellar.R;
import com.casperk.android.umbrellar.models.WeatherForecastForFiveDays;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caspernicus on 10/03/2018.
 */

public class TabForecastOverViewFragment extends Fragment {
    private static final String TAG = "TabForecastOverViewFragment";

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;

    private EditText mSearchWeatherForCityEditText;
    private Button mGetWeatherButton;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageTextView;
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
        mErrorMessageTextView = view.findViewById(R.id.error_message);
        mGetWeatherButton = view.findViewById(R.id.button_getweather);
       /* mWeatherForecastAdviceTextView = view.findViewById(R.id.weather_forecast_advice);
        mWeatherIconImageView = view.findViewById(R.id.weather_icon);*/

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter();
        mRecyclerView.setAdapter(mForecastAdapter);

        mGetWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWeatherForCityButtonClick(view);
            }
        });

        return view;
    }

    public void handleWeatherForCityButtonClick(View view) {
        if (mSearchWeatherForCityEditText.getText().length() == 0) {
            return;
        }

        requestQueue = Volley.newRequestQueue(getActivity());
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
}
