package com.example.katane.workoutcompanion.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katane.workoutcompanion.R;


/**
 * The 'default' fragment, shown when the app is started. Shows a dropdown allowing you to choose
 * your RoutineFragment, next to that a +, which allows you to create a new RoutineFragment, and under that the
 * available Workouts in your current RoutineFragment.
 * First place on the bottom bar.
 */
public class HomeFragment extends Fragment {
    private String TAG = "Fragment_Home";

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public String toString() {
        return "Home";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mainactivity_homefragment_layout, container, false);
        super.onViewCreated(view, savedInstanceState);

        android.support.v4.app.FragmentTransaction transaction= getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_home, RoutineListFragment.newInstance());
        transaction.commit();
        return view;
    }
}
