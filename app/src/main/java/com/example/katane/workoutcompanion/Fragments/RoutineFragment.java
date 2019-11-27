package com.example.katane.workoutcompanion.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katane.workoutcompanion.EditWorkoutActivity;
import com.example.katane.workoutcompanion.Helper_classes.Workout;
import com.example.katane.workoutcompanion.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class RoutineFragment extends Fragment {
    static String currentRoutine;
    private String TAG = "Fragment_Routine";
    private GridLayout gridLayout;
    private int gridLayoutRowHeight;
    private String currentWorkoutSelectedName;

    public static RoutineFragment newInstance(String routineToActivate) {
        RoutineFragment fragment = new RoutineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        currentRoutine = routineToActivate;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mainactivity_routinefragment_layout, container, false);
        super.onViewCreated(view, savedInstanceState);
        gridLayout = view.findViewById(R.id.fragment_routine_grid);
        fillRoutine(currentRoutine, getActivity());


        return view;

    }


    private void fillRoutine(final String routineName, Activity activity) {
        File RoutinePath = new File(activity.getFilesDir().getAbsoluteFile() + "/" + "Routines" + "/" + routineName);
        ArrayList<File> listOfWorkoutFiles = new ArrayList<File>(Arrays.asList(RoutinePath.listFiles()));
        for (int i = 0; i < listOfWorkoutFiles.size(); i++) {
            if (listOfWorkoutFiles.get(i).getName().equals("lastUsed.txt")) {
                Log.d(TAG, "Removed lastUsed from listOfWorkoutFiles");
                listOfWorkoutFiles.remove(i);
                break;
            }
        }

        Workout[] listOfWorkouts = new Workout[listOfWorkoutFiles.size()];
        Log.d(TAG, "Number of workouts in folder: " + Integer.toString(listOfWorkouts.length));
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView layout = null;

        for (int i = 0; i < (listOfWorkouts.length + 1); i++) {

            //if plus sign
            if (i == (listOfWorkouts.length)) {
                int plusSignHeight = 0;
                int plusSignWidth = 0;
                if (i == 0) {
                    plusSignHeight = Resources.getSystem().getDisplayMetrics().heightPixels / 4;
                    plusSignWidth = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if (i != 0) {
                    layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    plusSignHeight = gridLayoutRowHeight;
                    Log.d(TAG, "layout.getHeight: " + plusSignHeight);
                }
                CardView layout_add = (CardView) inflater.inflate(R.layout.mainactivity_routinefragment_addnew_cardview, gridLayout, false);
                ImageView plus_sign = layout_add.findViewById(R.id.add_workout_button_home);
                ViewGroup.LayoutParams params = layout_add.getLayoutParams();
                params.height = plusSignHeight;
                params.width = plusSignWidth;
                layout_add.setLayoutParams(params);
                gridLayout.addView(layout_add);
                plus_sign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), EditWorkoutActivity.class);
                        i.putExtra("RoutineName", RoutinePath.getName());
                        i.putExtra("ExistingWorkout", false);
                        startActivity(i);
                    }
                });
                break;
            }

            //If not the plus sign

            listOfWorkouts[i] = Workout.getWorkoutFromTemplate(activity, listOfWorkoutFiles.get(i).getParentFile().getName(), listOfWorkoutFiles.get(i).getName());

            Log.d(TAG, listOfWorkoutFiles.get(i).getParentFile().getName() + "    " + listOfWorkoutFiles.get(i).getName());
            Log.d(TAG, listOfWorkouts[i].getName());
            layout = (CardView) inflater.inflate(R.layout.mainactivity_routinefragment_workout_cardview, gridLayout, false);
            TextView name = layout.findViewById(R.id.workout_home_name);
            TextView dates = layout.findViewById(R.id.workout_home_dates);
            TextView exercises = layout.findViewById(R.id.workout_home_exercises);
            Log.d(TAG, "Trying to call getName on " + i + "index.");
            name.setText(listOfWorkouts[i].getName());
            if (listOfWorkouts[i].getDateLastUsed() != null) {
                DateFormat dateFormat = new SimpleDateFormat(getActivity().getResources().getString(R.string.date_format_for_last_used_workout));
                dates.setText("Last used: " + dateFormat.format(listOfWorkouts[i].getDateLastUsed()));
                dates.setTypeface(null, Typeface.ITALIC);
            }
            registerForContextMenu(layout);
            gridLayout.addView(layout);
            if (i == 0) {
                gridLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                gridLayoutRowHeight = gridLayout.getMeasuredHeight();
            }

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.routinefragment_routine_floatingcontextmenu, menu);
        TextView nameTextView = v.findViewById(R.id.workout_home_name);
        currentWorkoutSelectedName = nameTextView.getText().toString();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(getActivity().getBaseContext(), currentWorkoutSelectedName,
                Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), EditWorkoutActivity.class);
        i.putExtra("RoutineName", currentRoutine);
        i.putExtra("ExistingWorkout", true);
        i.putExtra("WorkoutName", currentWorkoutSelectedName);
        startActivity(i);



        currentWorkoutSelectedName = null;
        return super.onContextItemSelected(item);

    }
}
