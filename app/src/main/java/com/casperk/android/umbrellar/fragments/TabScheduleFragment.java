package com.casperk.android.umbrellar.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.casperk.android.umbrellar.R;
import com.casperk.android.umbrellar.models.WeatherForecast;
import com.casperk.android.umbrellar.models.WeatherForecastForFiveDays;
import com.casperk.android.umbrellar.utilities.NetworkUtils;
import com.casperk.android.umbrellar.utilities.RenderUtils;
import com.casperk.android.umbrellar.utilities.WeatherUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import me.tittojose.www.timerangepicker_library.TimeRangePickerDialog;

/**
 * Created by Caspernicus on 10/03/2018.
 */

public class TabScheduleFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimeRangePickerDialog.OnTimeRangeSelectedListener {
    private static final String TAG = "TabScheduleFragment";

    private RequestQueue _requestQueue;
    private Gson _gson;

    private TextView mCityTextView;
    private TextView mFeedbackTextView;
    private Button mPickScheduleBtn;
    private TextView mAdviceTextView;
    private TextView mTemperatureTextView;
    private ImageView mWeatherImageView;

    private String _inputtedCity;
    private int _day, _month, _year;
    private int _pickedDay, _pickedMonth, _pickedYear;
    private int _pickedStartHour, _pickedStartMinute, _pickedEndHour, _pickedEndMinute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            TimeRangePickerDialog tpd = (TimeRangePickerDialog) getActivity().getSupportFragmentManager()
                    .findFragmentByTag(TAG);
            if (tpd != null) {
                tpd.setOnTimeRangeSetListener(this);
            }
        }

        View view = inflater.inflate(R.layout.tab_schedule_fragment, container, false);
        mCityTextView = view.findViewById(R.id.tv_schedule_city);
        mFeedbackTextView = view.findViewById(R.id.tv_schedule_feedback);
        mPickScheduleBtn = view.findViewById(R.id.btn_schedule_pick_schedule);
        mAdviceTextView = view.findViewById(R.id.tv_schedule_advice);
        mTemperatureTextView = view.findViewById(R.id.tv_schedule_temperature);
        mWeatherImageView = view.findViewById(R.id.iv_schedule_weather_icon);

        mFeedbackTextView.setText("Geef een stad in en stel een begin en eind tijdstip in wanneer je buiten bent.");

        mPickScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(getActivity());
                editText.setHint("stad");

                new AlertDialog.Builder(getActivity())
                        .setTitle("Stad")
                        .setMessage("Geef een stad in.")
                        .setView(editText)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                _inputtedCity = editText.getText().toString();
                                goToDatePicker();
                            }
                        })
                        .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();

            }
        });

        return view;
    }

    private void goToDatePicker() {
        Calendar calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH);
        _day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), TabScheduleFragment.this, _year, _month, _day);
        datePickerDialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            TimeRangePickerDialog tpd = (TimeRangePickerDialog) getActivity().getSupportFragmentManager()
                    .findFragmentByTag(TAG);
            if (tpd != null) {
                tpd.setOnTimeRangeSetListener(this);
            }
        }

        mFeedbackTextView = getActivity().findViewById(R.id.tv_schedule_feedback);
        mPickScheduleBtn = getActivity().findViewById(R.id.btn_schedule_pick_schedule);
        mAdviceTextView = getActivity().findViewById(R.id.tv_schedule_advice);
        mTemperatureTextView = getActivity().findViewById(R.id.tv_schedule_temperature);
        mWeatherImageView = getActivity().findViewById(R.id.iv_schedule_weather_icon);

        mFeedbackTextView.setText("Geef een stad in en stel een begin en eind tijdstip in wanneer je buiten bent.");

        mPickScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCityTextView.setVisibility(View.INVISIBLE);
                final EditText editText = new EditText(getActivity());
                editText.setHint("stad");

                new AlertDialog.Builder(getActivity())
                        .setTitle("Stad")
                        .setMessage("Geef een stad in.")
                        .setView(editText)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                _inputtedCity = editText.getText().toString();
                                goToDatePicker();
                            }
                        })
                        .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        _pickedYear = year;
        _pickedMonth = month;
        _pickedDay = day;

        final TimeRangePickerDialog timePickerDialog = TimeRangePickerDialog.newInstance(TabScheduleFragment.this, true);
        timePickerDialog.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        _pickedStartHour = startHour;
        _pickedStartMinute = startMin;
        _pickedEndHour = endHour;
        _pickedEndMinute = endMin;

        if (endHour <= startHour && endMin <= startMin) {
            mFeedbackTextView.setText("Het begintijdstip mag niet later zijn dan het eindtijdstip.\nProbeer het nog eens.");
        }else if (pickedTimePeriodIsMoreThanFiveDaysInTheFuture()) {
            mFeedbackTextView.setText("Het eindtijdstip mag niet verder dan 5 dagen in de toekomst zijn.\nProbeer het nog eens.");
        }
        else {
            String startTime = String.format("%s:%02d", startHour, startMin);
            String endTime = String.format("%s:%02d", endHour, endMin);
            mFeedbackTextView.setText(String.format("%s/%02d/%02d",  _pickedYear, _pickedMonth, _pickedDay) + "  " + startTime + "  -  " + endTime);

            _requestQueue = Volley.newRequestQueue(getActivity());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
            _gson = gsonBuilder.create();

            fetchWeatherForecastForFiveDays(_inputtedCity);
        }
    }

    private boolean pickedTimePeriodIsMoreThanFiveDaysInTheFuture() {
        Date pickeEndDate = new GregorianCalendar(_pickedYear, _pickedMonth, _pickedDay, _pickedEndHour, _pickedEndHour).getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 4);
        calendar.add(Calendar.HOUR, 23);
        Date dateToCompareTo = calendar.getTime();

        return  pickeEndDate.after(dateToCompareTo);
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

        _requestQueue.add(request);
    }

    private final Response.Listener<String> onForecastLoaded = new Response.Listener<String>() {
        @SuppressLint("LongLogTag")
        @Override
        public void onResponse(String response) {
            final WeatherForecastForFiveDays weatherForecast = _gson.fromJson(response, WeatherForecastForFiveDays.class);

            Date beginDate = new GregorianCalendar(_pickedYear, _pickedMonth, _pickedDay, _pickedStartHour, _pickedStartMinute).getTime();
            Date endDate = new GregorianCalendar(_pickedYear, _pickedMonth, _pickedDay, _pickedEndHour, _pickedEndMinute).getTime();

            float maxTemp = 0f;
            float minTemp = 0f;
            boolean needsUmbrella = false;

            for (WeatherForecast forecast : weatherForecast.getForecastForFiveDays()) {
                Date dateOfForecast = new Date(forecast.getForecastDate() * 1000);
                if (dateOfForecast.after(endDate)) {
                    break;
                }

                if (dateOfForecast.before(endDate) && dateOfForecast.after(beginDate)) {
                    if (maxTemp < forecast.getMainWeatherInfo().getTemp_max()) maxTemp = forecast.getMainWeatherInfo().getTemp_max();
                    if (minTemp > forecast.getMainWeatherInfo().getTemp_min()) minTemp = forecast.getMainWeatherInfo().getTemp_min();
                    if (WeatherUtils.mustBringUmbrella(forecast.getWeatherConditions().get(0).getId())) needsUmbrella = true;
                }
            }

            if (needsUmbrella)
                mAdviceTextView.setText(WeatherUtils.NEEM_PARAPLU_MEE);
            else
                mAdviceTextView.setText(WeatherUtils.GEEN_PARAPLU_NODIG);

            mCityTextView.setVisibility(View.VISIBLE);
            mCityTextView.setText(_inputtedCity.toUpperCase());
            mTemperatureTextView.setText(String.format("Min: %s °C\nMax: %s °C", Math.round(minTemp), Math.round(maxTemp)));
            RenderUtils.renderWeatherIconFromResourceId(getActivity(), mWeatherImageView, needsUmbrella ? 200 : 800);

            Log.i(TAG, "Forecast geladen.");
        }
    };

    private final Response.ErrorListener onForecastError = new Response.ErrorListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onErrorResponse(VolleyError error) {

            String errorMessage;
            if (error.networkResponse.statusCode == 404)
                errorMessage = "Kon de weersvoorspelling voor de opgegeven stad niet vinden. Probeer het eens in het Engels.";
            else
                errorMessage = "Er is iets fout gegaan. Probeer het later nog eens.";

            mFeedbackTextView.setText(errorMessage);
            Log.e(TAG, error.toString());
        }
    };
}
