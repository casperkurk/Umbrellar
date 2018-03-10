package com.casperk.android.umbrellar.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.casperk.android.umbrellar.R;

/**
 * Created by Caspernicus on 10/03/2018.
 */

public class TabScheduleFragment extends Fragment {
    private static final String TAG = "TabScheduleFragment";

    private Button btnTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_schedule_fragment, container, false);
        btnTest = view.findViewById(R.id.btnTest);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Op button geklikt", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
