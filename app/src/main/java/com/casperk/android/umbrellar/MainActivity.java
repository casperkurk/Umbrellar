package com.casperk.android.umbrellar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchWeatherForCityEditText;
    private ProgressBar mLoadingIndicator;
    private TextView mWeatherResultTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchWeatherForCityEditText = findViewById(R.id.search_weather_city_input);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mWeatherResultTest = findViewById(R.id.weather_result_test);
    }

    public void getWeatherForCity(View view) {
        if (mSearchWeatherForCityEditText.getText().length() == 0) {
            return;
        }

        new GetWeatherTask().execute(mSearchWeatherForCityEditText.getText().toString());
    }

    private void getWeatherDataForCity() {

    }

    public class GetWeatherTask extends AsyncTask<String, Void, JSONObject> {
        private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/forecast?q=%s,BE&units=metric";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            //&appid=f071526db742a94be9d121a37641cf1a

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String city = params[0];
            URL weatherRequestUrl = null;

            try {
                weatherRequestUrl = new URL(String.format(OPEN_WEATHER_MAP_API, city));
                HttpURLConnection connection = (HttpURLConnection) weatherRequestUrl.openConnection();
                connection.addRequestProperty("x-api-key", getString(R.string.open_weather_maps_app_id));

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());

                // This value will be 404 if the request was not
                // successful
                if(data.getInt("cod") != 200){
                    return null;
                }

                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                mWeatherResultTest.setText(weatherData.toString());
            } else {
                mWeatherResultTest.setText("Oeps, er is iets mis gegaan.");
            }
        }
    }
}
