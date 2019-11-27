package com.example.katane.workoutcompanion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katane.workoutcompanion.Helper_classes.CardViewWithSlot;
import com.example.katane.workoutcompanion.Helper_classes.Slot;
import com.example.katane.workoutcompanion.Helper_classes.Workout;

import java.io.File;
import java.util.ArrayList;

public class EditWorkoutActivity extends AppCompatActivity {
    final String TAG = "EditWorkoutActivity";
    private File routineFile;
    private Boolean existingWorkoutBoolean;
    private File workoutFile;
    private Workout existingWorkout;
    private ArrayList<Slot> slots = new ArrayList<Slot>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editworkoutactivity_layout);
        LinearLayout layout = findViewById(R.id.content_create_workout_linearLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        readIntent(intent);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(workoutFile!=null){
            existingWorkout = Workout.getWorkoutFromTemplate(this, routineFile.getName(), workoutFile.getName());

            slots.addAll(new ArrayList<>(existingWorkout.getSlots()));

            ArrayList<Slot> slotsInSlot = new ArrayList<>();

            for(int i = 0; i < slots.size(); i++){
                Log.d(TAG, "first for loop" + i);
                CardView cardView = (CardView) inflater.inflate(R.layout.editworkoutactivity_slot_cardview, layout,false);

                //Left column of slot cardView
                TextView name = cardView.findViewById(R.id.editworkoutactivity_slot_cardview_slot_name);
                name.setText("Slot " + (i+1));



                //Right column of slot cardView
                slotsInSlot.clear();
                slotsInSlot.addAll(slots.get(i).getListOfSlots());
                LinearLayout rightColumnLinearLayout = cardView.findViewById(R.id.editworkoutactivity_slot_cardview_slot_rightcolumnlinearlayout);


                if(slotsInSlot.size()==0 && slots.get(i).getExercise()!=null){
                    TextView slotOrExercise = (TextView) inflater.inflate(R.layout.editworkoutactivity_slot_cardview_slotorexercise_textview,rightColumnLinearLayout, false);
                    slotOrExercise.setText(slots.get(i).getExercise().getName());
                    rightColumnLinearLayout.addView(slotOrExercise);
                }


                for(int j = 0; j < slotsInSlot.size(); j++){
                    Log.d(TAG, "second for loop" + j);
                    TextView slotOrExercise = (TextView) inflater.inflate(R.layout.editworkoutactivity_slot_cardview_slotorexercise_textview,rightColumnLinearLayout, false);
                    Slot currentSlot = slotsInSlot.get(j);
                    if(currentSlot.getExercise()!=null){
                        if(j == currentSlot.getCurrentProgressionLevel() && slots.get(i).isProgressive()){
                            slotOrExercise.setTypeface(null, Typeface.BOLD);
                        }
                        Log.d(TAG,currentSlot.getExercise().getName());
                        slotOrExercise.setText(currentSlot.getExercise().getName());
                    }
                    rightColumnLinearLayout.addView(slotOrExercise);
                }
                layout.addView(cardView);
            }
        }


    }



    public void readIntent(Intent i){

        try{
            routineFile = new File(getFilesDir().getAbsoluteFile() + "/Routines/" + i.getStringExtra("RoutineName"));
            Log.d(TAG, i.getStringExtra("RoutineName") + "exists!");
            if(!routineFile.exists()){
                Log.d(TAG, "Routine passed in string doesn't exist!");
            }
        }catch(Exception e){
            Log.d(TAG, "Trying to create a file out of string passed to EditWorkoutActivity in intent:" + e.toString());
            finish();
        }
        try {
            existingWorkoutBoolean = i.getBooleanExtra("ExistingWorkout", false);
            Log.d(TAG, "ExistingWorkout boolean is " + existingWorkoutBoolean);
        }catch(Exception e){
            Log.d(TAG, "Couldn't find boolean ExistingWorkout in intent, assuming false: " + e.toString());
        }

        if(existingWorkoutBoolean){
            try{
                workoutFile = new File(routineFile.getAbsolutePath() + "/" + i.getStringExtra("WorkoutName"));
                if(!workoutFile.exists()){
                    Log.d(TAG, "Workout passed in intent doesn't exist!");
                }
            }catch(Exception e){
                Log.d(TAG, "Workout path passed in intent is wrong" + e.toString());
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editworkoutactivity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.workoutCreator_menu_finish:
                saveWorkout();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveWorkout(){

    }
}
