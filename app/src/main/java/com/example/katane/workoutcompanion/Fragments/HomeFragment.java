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

/*    private static final String TAG = "HomeFragment";
    private GridLayout gridLayout;
    private Spinner homeSpinner;
    private File currentRoutineFolder;
    private ArrayList<File> listOfRoutineFolders;
    private int lastSelectedSpinnerPosition = 0;

    public String toString() {
        return "HomeFragment";
    }

    private Dialog editRoutineNameDialog;
    private Dialog createNewRoutineDialog;
    private Dialog confirmDeleteDialog;
    private File routineFolder = null;*/


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


    /*
        createTestRoutines();


        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.mainactivity_homefragment_layout, container, false);
        homeSpinner = view.findViewById(R.id.home_spinner);
        final LinkedList<String> spinnerArray = new LinkedList<String>();

        */
        /*Trying to list Routines in dir*//*

        try {
            routineFolder = new File(getActivity().getFilesDir().getAbsoluteFile() + "/Routines/");
            listOfRoutineFolders = new ArrayList<>(Arrays.asList(routineFolder.listFiles()));
            for (File file : listOfRoutineFolders) {
                if (file.isDirectory()) {
                    spinnerArray.add(file.getName());
                }
            }
        }catch(Exception e){
            Log.d(TAG, e.toString());
        }

        */
        /*Adds routines to the spinner*//*

        spinnerArray.add("Create a new routine");
        final int spinnerArraySize = spinnerArray.size();
        gridLayout = view.findViewById(R.id.home_grid);
        final ArrayAdapter<String> homeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        homeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homeSpinner.setAdapter(homeSpinnerAdapter);
        homeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==spinnerArraySize-1){
                    editRoutineNameDialog = new Dialog(getActivity());
                    editRoutineNameDialog.setContentView(R.layout.mainactivity_homefragment_editroutine_dialog);

                    Toast.makeText(getActivity(), "This is my Toast message!",
                            Toast.LENGTH_LONG).show();
                    homeSpinner.setSelection(lastSelectedSpinnerPosition);
                    lastSelectedSpinnerPosition = i;
                    return;
                }
                lastSelectedSpinnerPosition = i;
                gridLayout.removeAllViews();
                try {
                    fillRoutine(listOfRoutineFolders.get(i), getActivity());
                    currentRoutineFolder = listOfRoutineFolders.get(i);
                }
                catch(Exception e){
                    Log.d(TAG, "line 133" + e.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FloatingActionButton editNameFAB = view.findViewById(R.id.home_edit_routine_fab);
        editNameFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editRoutineNameDialog = new Dialog(getActivity());
                editRoutineNameDialog.setContentView(R.layout.mainactivity_homefragment_editroutine_dialog);

                final EditText editRoutineName = editRoutineNameDialog.findViewById(R.id.home_edit_routine_dialog_edit_name);
                editRoutineName.setText(currentRoutineFolder.getName());
                int pos = editRoutineName.getText().length();
                editRoutineName.setSelection(pos);

                Button saveRoutineName = editRoutineNameDialog.findViewById(R.id.home_edit_routine_dialog_save);
                editRoutineName.setEnabled(true);saveRoutineName.setEnabled(true);
                saveRoutineName.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        int i = listOfRoutineFolders.indexOf(currentRoutineFolder);
                        Log.d(TAG, "currentRoutineFolder is " + currentRoutineFolder.getName());
                        Log.d(TAG, "listOfRoutineFolders is " + listOfRoutineFolders.stream().map(File::getName)
                                .collect(Collectors.joining(", ")));
                        Log.d(TAG, "index of currentRoutineFolder is " + listOfRoutineFolders.indexOf(currentRoutineFolder));


                        String newRoutineName = editRoutineName.getText().toString();
                        String routinesFolderString = currentRoutineFolder.getParentFile().getAbsolutePath();
                        String newRoutineFolderString = routinesFolderString + "/" + newRoutineName;
                        //DONE: RoutineFragment name change must also edit all files etc (or change the way routines are created so that you don't need to)
                        Log.d(TAG, "The new routine path is: " + newRoutineFolderString);
                        File newRoutineFolder = new File(newRoutineFolderString);
                        Log.d(TAG, currentRoutineFolder.exists() + " current before");
                        Log.d(TAG, newRoutineFolder.exists() + " new before");


                        if(!newRoutineFolder.exists() && !listOfRoutineFolders.contains(newRoutineFolder)){
                            if(currentRoutineFolder.renameTo(newRoutineFolder)){
                                Log.d(TAG, "Renamed " + currentRoutineFolder.toString() + " to " + newRoutineFolder.toString());
                                currentRoutineFolder = newRoutineFolder;
                                listOfRoutineFolders = new ArrayList<>(Arrays.asList(routineFolder.listFiles()));
                                Log.d(TAG, "listOfRoutineFolders is " + listOfRoutineFolders.stream().map(File::getName)
                                        .collect(Collectors.joining(", ")));
                                spinnerArray.remove(i);
                                spinnerArray.add(i, newRoutineName);
                                homeSpinnerAdapter.notifyDataSetChanged();
                            }
                        }

                        editRoutineNameDialog.cancel();

                    }
                });

                editRoutineNameDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                editRoutineNameDialog.show();


            }
        });

        FloatingActionButton deleteFAB = view.findViewById(R.id.home_delete_routine_fab);
        deleteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog = new Dialog(getActivity());
                confirmDeleteDialog.setContentView(R.layout.mainactivity_homefragment_deleteroutine_dialog);
                TextView confirmDeleteText = confirmDeleteDialog.findViewById(R.id.home_delete_routine_dialog_confirmationText);
                confirmDeleteText.setText(getString(R.string.deleteRoutineConfirmationText, currentRoutineFolder.getName()));
                confirmDeleteDialog.show();

                Button cancelDeleteRoutine = confirmDeleteDialog.findViewById(R.id.home_delete_routine_dialog_cancel);
                cancelDeleteRoutine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmDeleteDialog.cancel();
                    }
                });

                Button deleteRoutine = confirmDeleteDialog.findViewById(R.id.home_delete_routine_dialog_delete);
                deleteRoutine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = listOfRoutineFolders.indexOf(currentRoutineFolder);
                        if(listOfRoutineFolders.size()==1){
                            Toast.makeText(getActivity(), "You cannot delete the last routine.",
                                    Toast.LENGTH_LONG).show();
                            confirmDeleteDialog.cancel();
                        }
                        try{
                            Log.d(TAG, "listOfRoutineFolders before calling delete is " + listOfRoutineFolders.stream().map(File::getName)
                                    .collect(Collectors.joining(", ")));

                            RoutineMethods.deleteRoutine(currentRoutineFolder.getName(), getActivity(), TAG);
                            listOfRoutineFolders = new ArrayList<>(Arrays.asList(routineFolder.listFiles()));
                            Log.d(TAG, "listOfRoutineFolders after calling delete is " + listOfRoutineFolders.stream().map(File::getName)
                                    .collect(Collectors.joining(", ")));
                            currentRoutineFolder = listOfRoutineFolders.get(listOfRoutineFolders.size()-1);
                            spinnerArray.remove(i);
                            homeSpinnerAdapter.notifyDataSetChanged();
                            homeSpinner.setSelection(listOfRoutineFolders.indexOf(currentRoutineFolder));
                        }
                        catch (Exception e){
                            Log.d(TAG, e.toString());
                        }


                        confirmDeleteDialog.cancel();
                    }
                });

            }
        });


        return view;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        MenuMethods.inflateMenuHome(menu, inflater);
    }





    */
/**
 * Function that takes in a RoutineFragment path, finds all the Workouts in it and fills the view,
 * which consists of the left side and the right side, with the template version of the
 * Workouts in the RoutineFragment
 *//*

    private void fillRoutine(final File RoutinePath, Activity activity){
        File[] listOfWorkoutFiles = RoutinePath.listFiles();
        Log.d(TAG, "Number of workouts in folder: " + Integer.toString(listOfWorkoutFiles.length));
        Workout[] listOfWorkouts = new Workout[listOfWorkoutFiles.length];
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView layout = null;

        for(int i=0; i<(listOfWorkoutFiles.length+1); i++) {

            if(i==(listOfWorkoutFiles.length)){
                int plusSignHeight = Resources.getSystem().getDisplayMetrics().heightPixels/4;
                int plusSignWidth = ViewGroup.LayoutParams.MATCH_PARENT;

                if(i!=0){
                    layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    plusSignHeight = layout.getMeasuredHeight();
                    plusSignWidth = layout.getMeasuredWidth();
                    Log.d(TAG, "layout.getHeight: " +  plusSignHeight);
                }
                FrameLayout layout_add = (FrameLayout) inflater.inflate(R.layout.mainactivity_routinefragment_addnew_cardview, gridLayout, false);
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
                        i.putExtra("RoutinePath", RoutinePath.toString());
                        i.putExtra("ExistingWorkout", false);
                        startActivity(i);
                    }
                });
                break;
            }

            listOfWorkouts[i] = Workout.getWorkoutFromTemplate(activity, listOfWorkoutFiles[i].getParentFile().getName(), listOfWorkoutFiles[i].getName());

            layout = (CardView) inflater.inflate(R.layout.mainactivity_routinefragment_workout_cardview, gridLayout, false);
            TextView name = layout.findViewById(R.id.workout_home_name);
            TextView dates = layout.findViewById(R.id.workout_home_dates);
            TextView exercises = layout.findViewById(R.id.workout_home_exercises);
            name.setText(listOfWorkouts[i].getName());
            if(listOfWorkouts[i].getDateLastStarted()!= null && listOfWorkouts[i].getDateFinished() != null){
                dates.setText("Last started: " + listOfWorkouts[i].getDateLastStarted().toString() + ".\nLast finished: " + listOfWorkouts[i].getDateFinished().toString());
            }
            else if(listOfWorkouts[i].getDateLastStarted()== null && listOfWorkouts[i].getDateFinished() != null){
                dates.setText("Has never been started.\nLast finished: " + listOfWorkouts[i].getDateFinished().toString());
            }
            else if(listOfWorkouts[i].getDateLastStarted() != null && listOfWorkouts[i].getDateFinished() == null){
                dates.setText("Last started: " + listOfWorkouts[i].getDateLastStarted().toString() + ".\nHas never been finished");
            }
            else if(listOfWorkouts[i].getDateLastStarted() == null && listOfWorkouts[i].getDateFinished() == null){
                dates.setText("Has never been started.\nHas never been finished.");
            }


            registerForContextMenu(layout);
            gridLayout.addView(layout);

        }
    }

    public void createTestRoutines(){
        Exercise deadlift1 = new Exercise("Deadlifts", 11, WorkoutCreatorHelpers.createSetsRepsList(5,1));
        Exercise barbellRows = new Exercise("Barbell rows",1, WorkoutCreatorHelpers.createSetsRepsList(5,5));
        Exercise ex2 = new Exercise("ex2", 69,WorkoutCreatorHelpers.createSetsRepsList(5,5));

        LinkedList<Exercise> exerciseList = new LinkedList<>(Arrays.asList(deadlift1, barbellRows));
        LinkedList<Exercise> exerciseList2 = new LinkedList<>(Arrays.asList(deadlift1, barbellRows,ex2));
        LinkedList<Exercise> exerciseList3 = new LinkedList<>(Arrays.asList(barbellRows,ex2));
        final Workout Workout = new Workout("PAPAJ3", exerciseList);
        final Workout Workout2 = new Workout("Workout 2", exerciseList2);
        final Workout Workout3 = new Workout("Workout 3", exerciseList3);


        Workout.saveTemplate(getActivity(),"Routine3" );
        Workout2.saveTemplate(getActivity(),"Routine3");
        Workout3.saveTemplate(getActivity(),"Routine3");

        Workout.saveWorkout(getActivity(), "Routine3");
        Workout2.saveWorkout(getActivity(), "Routine3");
        Workout3.saveWorkout(getActivity(),"Routine3");
    }



*/
}
