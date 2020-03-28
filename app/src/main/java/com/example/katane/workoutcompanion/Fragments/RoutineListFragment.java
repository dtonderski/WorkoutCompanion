package com.example.katane.workoutcompanion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.katane.workoutcompanion.Helper_classes.Exercise;
import com.example.katane.workoutcompanion.Helper_classes.RoutineMethods;
import com.example.katane.workoutcompanion.Helper_classes.Slot;
import com.example.katane.workoutcompanion.Helper_classes.Workout;
import com.example.katane.workoutcompanion.Helper_classes.WorkoutCreatorHelpers;
import com.example.katane.workoutcompanion.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class RoutineListFragment extends Fragment {
    private static final String TAG = "Fragment_RoutineList";
    private File routineFolder;
    private List<File> listOfRoutineFolders;


    public static RoutineListFragment newInstance() {
        RoutineListFragment fragment = new RoutineListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createTestRoutines();


        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.mainactivity_routinelistfragment_layout, container, false);

        /*Tries to populate the view with routines*/
        try {
            routineFolder = new File(getActivity().getFilesDir().getAbsoluteFile() + "/Routines/");
            listOfRoutineFolders = Arrays.asList(routineFolder.listFiles());
            Collections.sort(listOfRoutineFolders, RoutineMethods.byLastUsed(getActivity(), TAG));


            for (File file : listOfRoutineFolders) {
                if (file.isDirectory()) {
                    Log.d(TAG, file.getName() + RoutineMethods.getRoutineLastUsedDate(file.getName(), getActivity(), TAG));
                    CardView routineLayout = (CardView) inflater.inflate(R.layout.mainactivity_routinelistfragment_routine_cardview, view, false);
                    TextView routineName = routineLayout.findViewById(R.id.fragment_routine_list_routine_routine_name);

                    routineName.setText(file.getName());
                    TextView routineLastUsedDate = routineLayout.findViewById(R.id.fragment_routine_list_routine_routine_last_used_date);
                    DateFormat dateFormat = new SimpleDateFormat(getActivity().getResources().getString(R.string.date_format_for_last_used_routine));
                    routineLastUsedDate.setText("Last used: " + dateFormat.format(RoutineMethods.getRoutineLastUsedDate(file.getName(), getActivity(), TAG)));
                    Log.d(TAG, "drawing view");

                    routineLayout.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            RoutineMethods.setRoutineLastUsedDate(new Date(), file.getName(), getActivity(), TAG);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_home, RoutineFragment.newInstance(file.getName()));
                            transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                    view.addView(routineLayout);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return view;
    }

    public void createTestRoutines(){



        Exercise deadlift1 = new Exercise(true,"Deadlifts", 11.0, WorkoutCreatorHelpers.createSetsRepsList(5,1));
        Exercise barbellRows = new Exercise(true,"Barbell rows",1.0, WorkoutCreatorHelpers.createSetsRepsList(5,5));
        Exercise ex2 = new Exercise(true,"ex2", 69.0,WorkoutCreatorHelpers.createSetsRepsList(5,5));

        Slot slot1 = new Slot();
        slot1.setExercise(deadlift1);
        Slot slot2 = new Slot();
        slot2.setExercise(barbellRows);
        Slot slot3 = new Slot();
        slot3.setExercise(ex2);
        Slot slot4 = new Slot();
        slot4.setExercise(deadlift1);

        Slot slot5 = new Slot();
        slot5.addSlot(slot4);
        slot5.addSlot(slot3);
        slot5.setProgressive(true);
        slot5.setCurrentProgressionLevel(1);

        Slot slot6 = new Slot();

        LinkedList<Slot> exerciseList = new LinkedList<>(Arrays.asList(slot1, slot2, slot5));
        LinkedList<Slot> exerciseList2 = new LinkedList<>(Arrays.asList(slot1, slot2, slot3));
        LinkedList<Slot> exerciseList3 = new LinkedList<>(Arrays.asList(slot2,slot3));

        final Workout Workout = new Workout("PAPAJ3", exerciseList);
        final Workout Workout2 = new Workout("Workout 2", exerciseList2);
        final Workout Workout3 = new Workout("Workout 3", exerciseList3);


        Workout.saveTemplate(getActivity(),"Routine1" );
        Workout2.saveTemplate(getActivity(),"Routine2");
        Workout3.saveTemplate(getActivity(),"Routine3");

        Workout.saveWorkout(getActivity(), "Routine1");
        Workout2.saveWorkout(getActivity(), "Routine2");
        Workout3.saveWorkout(getActivity(),"Routine3");

        RoutineMethods.setRoutineLastUsedDate(new Date(), "Routine3", getActivity(), TAG);
        RoutineMethods.setRoutineLastUsedDate(new Date(), "Routine1", getActivity(), TAG);
        RoutineMethods.setRoutineLastUsedDate(new Date(), "Routine2", getActivity(), TAG);

    }





}
