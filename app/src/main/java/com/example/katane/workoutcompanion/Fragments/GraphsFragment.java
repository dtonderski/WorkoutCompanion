package com.example.katane.workoutcompanion.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katane.workoutcompanion.R;


/**
 * The fragment in which progress graphs will be shown. The exact design is yet to be determined.
 * Third place on the bottom bar.
 */
public class GraphsFragment extends Fragment {



    public String toString(){
        return "Graphs";
    }




    public static GraphsFragment newInstance() {
        GraphsFragment fragment = new GraphsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mainactivity_graphsfragment_layout, container, false);
    }

}
