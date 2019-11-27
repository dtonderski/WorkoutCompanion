package com.example.katane.workoutcompanion.Helper_classes;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Slot implements Serializable{

    /** A Slot can contain a list of slots XOR an exercise */

    private ArrayList<Slot> listOfSlots = new ArrayList<>();
    private Exercise exercise;

    /** A Slot cannot be both alternating and progressive */

    private Boolean alternating;
    private Boolean progressive;

    /** Goes from 0 to inf, only relevant (and initialized) for progressive*/
    private int currentProgressionLevel;

    public Slot(){}

    public Exercise nextExercise(String TAG){
        if(exercise!=null){
            return exercise;
        }
        else if(alternating){
            return(listOfSlots.get(0).nextExercise(TAG));
        }
        else if(progressive){
            return(listOfSlots.get(currentProgressionLevel).nextExercise(TAG));
        }
        else{
            Log.d(TAG,"There is no next exercise!");
            return(null);
        }
    }

    public void finish(Double liftedWeight, List<Integer> liftedSetsRepsOrSetsTimes){
        if(exercise!=null){
            exercise.finish(liftedWeight, liftedSetsRepsOrSetsTimes);
        }
        else if(listOfSlots.size()>0){
            if(alternating){
                listOfSlots.get(0).finish(liftedWeight, liftedSetsRepsOrSetsTimes);
                alternate();
            }
            else if(progressive){
                if(listOfSlots.size()<currentProgressionLevel-1){
                    currentProgressionLevel++;
                }
            }
        }
    }


    private void alternate(){
        if(listOfSlots.size()>1){
            ArrayList<Slot> copyOfList = new ArrayList<>(listOfSlots.subList(0, listOfSlots.size()-1));
            Slot temporaryElement = listOfSlots.get(listOfSlots.size());
            listOfSlots.clear();
            listOfSlots.add(temporaryElement);
            listOfSlots.addAll(copyOfList);
        }
    }

    public void setExercise(Exercise exercise){
        if(listOfSlots.size()==0){
            this.exercise = exercise;
        }
    }

    public void removeExercise(){
        exercise = null;
    }

    public void addSlot(Slot slot){
        if(exercise == null){
            listOfSlots.add(slot);
        }
    }

    public void removeSlot(int index){
        listOfSlots.remove(index);
    }


    public void setAlternating(Boolean alternating) {
        if(progressive!=null){
            if(!progressive){
                this.alternating = alternating;
            }
            else{
                throw new IllegalArgumentException("A Slot cannot be both alternating and progressive!");
            }
        }
        else{
            this.alternating = alternating;
        }


    }

    public void setProgressive(Boolean progressive){
        if(alternating!=null){
            if(!alternating){
                this.progressive = progressive;
            }
            else{
                throw new IllegalArgumentException("A Slot cannot be both alternating and progressive!");
            }
        }
        else{
            this.progressive = progressive;
        }

    }

    public ArrayList<Slot> getListOfSlots() {
        return listOfSlots;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getCurrentProgressionLevel() {
        return currentProgressionLevel;
    }

    public Boolean isAlternating() {
        if(alternating!=null){
            return alternating;
        }
        return false;    }

    public Boolean isProgressive() {
        if(progressive!=null){
            return progressive;
        }
        Log.d("EditWorkoutActivity", "Progressive is " + progressive);
        return false;
    }

    public void setCurrentProgressionLevel(int currentProgressionLevel) {
        this.currentProgressionLevel = currentProgressionLevel;
    }
}
