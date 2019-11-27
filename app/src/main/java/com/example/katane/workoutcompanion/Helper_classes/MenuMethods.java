package com.example.katane.workoutcompanion.Helper_classes;

import android.view.Menu;
import android.view.MenuInflater;

import com.example.katane.workoutcompanion.R;

public class MenuMethods {

    public static void inflateMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_default, menu);
    }

    public static void inflateMenuHome(Menu menu, MenuInflater inflater){
        inflateMenu(menu, inflater);
    }

}
