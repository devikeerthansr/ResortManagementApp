package com.example.resortmanagement.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.resortmanagement.DashboardActivity;
import com.example.resortmanagement.R;
import com.example.resortmanagement.constant.Constants;

public class AmenityFragment extends Fragment implements View.OnClickListener {
    Button excursionBtn;
    Button restoBtn;
    Button spaBtn;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id== R.id.excursion_btn){
            Fragment rFragment = new ExcursionFragment();
            ((DashboardActivity)getActivity()).goToNewFragment(rFragment, Constants.EXCURSION);
        } else if (id== R.id.restaurant_btn){
            Fragment rFragment = new RestaurantFragment();
            ((DashboardActivity)getActivity()).goToNewFragment(rFragment,Constants.RESTAURANT);
        } else if (id== R.id.spa_btn){
            Fragment rFragment = new SpaFragment();
            ((DashboardActivity)getActivity()).goToNewFragment(rFragment,Constants.SPA);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_amenity, container, false);
        excursionBtn=(Button)rootView.findViewById(R.id.excursion_btn);
        excursionBtn.setOnClickListener(this);
        restoBtn=(Button)rootView.findViewById(R.id.restaurant_btn);
        restoBtn.setOnClickListener(this);
        spaBtn=(Button)rootView.findViewById(R.id.spa_btn);
        spaBtn.setOnClickListener(this);
        return rootView;
    }
}
