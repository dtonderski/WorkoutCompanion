package com.example.katane.workoutcompanion.Helper_classes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.katane.workoutcompanion.MainActivity;
import com.example.katane.workoutcompanion.R;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Workout implements Serializable{
    private String name;
    private List<Slot> slots;
    private Date dateLastUsed;


    /** Constructor */
    public Workout(String name, List<Slot> slots){
        this.slots = slots;
        this.name = name;
        this.dateLastUsed = new Date();
    }



    /** Serializability */

    public void saveTemplate(Context context, String routineName) {
        File directory = new File(context.getFilesDir().getAbsoluteFile(), "/" + "Routines" + "/" + routineName + "/" + name);
        Boolean mkdirs_successful = directory.mkdirs();
        if(mkdirs_successful){
            Log.d("Workout", "Created new dir");
        }
        try {
            File file = new File(context.getFilesDir().getAbsoluteFile() + "/" + "Routines" + "/" + routineName + "/" + name + "/" + name + ".ser");
            Log.d("Workout", "Created the template file!");
            Log.d("Workout", "Can read: " + file.canRead());
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Workout getWorkoutFromTemplate(Context context, String routineName, String WorkoutName){
        Workout Workout=null;
        try{
            File file = new File(context.getFilesDir().getAbsoluteFile() +"/" + "Routines" + "/" + routineName + "/" + WorkoutName + "/" + WorkoutName + ".ser");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Workout = (Workout) in.readObject();
            in.close(); fileIn.close();
            Log.d("Workout", "Read the template file!");
            Log.d("Workout", Workout.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Workout;
    }

    public void saveWorkout(Context context, String routineName){
        DateFormat dateFormat = new SimpleDateFormat(context.getResources().getString(R.string.date_format_for_saved_Routines));
        Date date = new Date();
        File file = new File(context.getFilesDir().getAbsoluteFile() + "/" + "Routines" + "/" + routineName + "/" + name + "/" + dateFormat.format(date) + ".ser");
        try {
            Log.d("Workout", "Created the template file!");
            Log.d("Workout", "Can read: " + file.canRead());
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            Log.d("Workout", e.toString());
        }
    }



    /**Getters and setters */
    public void addSlot(Slot slot){
        slots.add(slot);
    }

    public void removeSlot(int index){
        slots.remove(index);
    }

    public void setDateLastUsed(Date dateLastUsed) {
        this.dateLastUsed = dateLastUsed;
    }

    public Date getDateLastUsed() {
        return dateLastUsed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    /*
    private String name;
    private List<Exercise> exercises;
    private Date dateLastUsed;
    private List<List<Exercise>> listOfListsOfAlternateExercises;
    private List<Integer> switchToAlternateFrequency;
    private List<Integer> hasSwitchedCounter;

    */
/**
     * Constructors
     *//*

    public Workout(Exercise exercise){
        exercises = new LinkedList<>();
        exercises.add(exercise);

    }

    public Workout(String Name, List<Exercise> exerciselist){name= Name; exercises=exerciselist;}






        */
/**
     *Function that adds alternative Routines. Adds the alternate RoutineFragment in the first list
     * in the 'list of lists' where there is space in the right position. If there is no space,
     * creates a new list and adds it to the end of the 'list of lists'
     *//*

    public void addAlternateRoutine(Exercise alternateExercise, int alternateExercisePosition) throws IllegalArgumentException {
        if(alternateExercisePosition>=exercises.size()){
            throw new IllegalArgumentException("The alternate exercise position cannot be larger than the size of the exercise list");
        }
        if(listOfListsOfAlternateExercises==null){
            LinkedList<Exercise> firstRowOfList = new LinkedList<>();
            listOfListsOfAlternateExercises = new LinkedList<>();
            listOfListsOfAlternateExercises.add(firstRowOfList);
        }
        for(List<Exercise> exerciseList : listOfListsOfAlternateExercises){

            if(exerciseList.size()==0){
                if(alternateExercisePosition==0){
                    exerciseList.add(alternateExercise);
                    return;
                }
                else{
                    exerciseList.add(null);
                }
            }

            if(exerciseList.size() == alternateExercisePosition){
                exerciseList.add(alternateExercise);
                return;
            }

            if(exerciseList.size()<alternateExercisePosition) {
                for (int i = exerciseList.size(); i < alternateExercisePosition; i++) {
                    exerciseList.add(null);
                }
                exerciseList.add(alternateExercise);
                return;
            }
            else if(exerciseList.size()>alternateExercisePosition){
                if(exerciseList.get(alternateExercisePosition)==null){
                    exerciseList.set(alternateExercisePosition,alternateExercise);
                    return;

                }
            }
        }
        LinkedList<Exercise> nextExerciseList = new LinkedList<>();
        for (int i = 0; i < alternateExercisePosition; i++) {
            nextExerciseList.add(null);
        }
        nextExerciseList.add(alternateExercise);
        listOfListsOfAlternateExercises.add(nextExerciseList);
    }


    public static Workout getWorkoutFromDate(Context context, String routineName, String WorkoutName, final String prefix){
        Workout Workout=null;
        File dir = new File(context.getFilesDir().getAbsoluteFile() +"/" + "Routines" + "/" + routineName + "/" + WorkoutName + "/");
        try{
            File[] candidates = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().startsWith(prefix);
                }
            });
            if(candidates==null || candidates.length==0){
                throw new Exception("No file starts with this prefix!");
            }
            FileInputStream fileIn = new FileInputStream(candidates[0]);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Workout = (Workout) in.readObject();
            in.close(); fileIn.close();
            Log.d("Workout", "Read the template file!");
            Log.d("Workout", Workout.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Workout;
    }




*/
/*    *//*
*/
/**
     * Function to be called after a Workout is done, includes switching to alternate Routines, progressing weight, setting dateFinished;
     *//*
*/
/*
    public void finishRoutine(){
        *//*
*/
/*
        saveRoutine(context);*//*
*/
/*
        for(Exercise exercise : exercises){
            exercise.addProgress();
        }
        switchAlternate();
    }*//*

    */
/**
     * Switch alternate Routines
     *//*

    public void switchAlternate(){

        for(int i = 0; i<exercises.size(); i++){
            */
/**
             * Add one "switch" to the switch counter
             *//*

            if( switchToAlternateFrequency.get(i) != 0 ){
                hasSwitchedCounter.set(i, hasSwitchedCounter.get(i)+1);
            }
            */
/**
             * call advanceRoutines on the column and reset the counter
             *//*

            if(switchToAlternateFrequency.get(i)<=hasSwitchedCounter.get(i)){
                advanceRoutines(i);
                hasSwitchedCounter.set(i, 0);
            }
        }
    }

    public void advanceRoutines(int position){
        if(listOfListsOfAlternateExercises==null){
            return;
        }

        if(listOfListsOfAlternateExercises.get(0)==null){
            return;
        }

        if(position>listOfListsOfAlternateExercises.size()){
            return;
        }

        if(listOfListsOfAlternateExercises.get(0).get(position)==null){
            return;
        }



        int amountOfExercises=0;
        for(int i = 0; i<listOfListsOfAlternateExercises.size(); i++){
            if(listOfListsOfAlternateExercises.get(i).size()<=position){
                break;
            }
            if(listOfListsOfAlternateExercises.get(i).get(position)!=null){
                amountOfExercises=i;
            }
        }

        Exercise tempExercise;
        List<Exercise> tempList1;
        tempExercise = listOfListsOfAlternateExercises.get(amountOfExercises).get(position);
        for(int i = amountOfExercises; i>-1; i--){
            if(i>0){
                tempList1 = listOfListsOfAlternateExercises.get(i);
                tempList1.set(position,listOfListsOfAlternateExercises.get(i-1).get(position));
                listOfListsOfAlternateExercises.set(i, tempList1);
            }
            else if( i==0 ){
                tempList1 = listOfListsOfAlternateExercises.get(i);
                tempList1.set(position,exercises.get(position));
                listOfListsOfAlternateExercises.set(i, tempList1);
            }
        }
        exercises.set(position, tempExercise);




    }



    */
/**
     * Helper methods
     *//*


    public String toString(){
        String WorkoutString= name;
        WorkoutString += "Current exercises:\n";
        for(Exercise exercise : exercises){
            if(exercise!=null){
                WorkoutString += exercise.toString() + "\n";
            }
            else{
                WorkoutString += "NULL\n";
            }
        }
        if(listOfListsOfAlternateExercises==null){
            return WorkoutString;
        }
        int rowCounter = 0;
        for(List<Exercise> exerciseList : listOfListsOfAlternateExercises){
            rowCounter++;
            WorkoutString += "Alternative exercise row " + rowCounter + ":\n";
            for(Exercise exercise : exerciseList){
                if(exercise!=null){
                    WorkoutString += exercise.toString() + "\n";
                }
                else{
                    WorkoutString += "NULL\n";
                }

            }
        }
        return WorkoutString;
    }

    */
/**
     * Setters and getters
     *//*


    public String getName(){
        return name;
    }

    public void setName(String Name){
        name=Name;
    }

    public void setFrequency(int position, int frequency){
        if(switchToAlternateFrequency==null){
            switchToAlternateFrequency = new LinkedList<>();
            switchToAlternateFrequency.add(frequency);
            hasSwitchedCounter = new LinkedList<>();
            hasSwitchedCounter.add(0);
        }
        if(position>=switchToAlternateFrequency.size()){
            for(int i = switchToAlternateFrequency.size(); i<position; i++){
                switchToAlternateFrequency.add(0);
                hasSwitchedCounter.add(0);

            }
            switchToAlternateFrequency.add(frequency);
            hasSwitchedCounter.add(0);

        }
        else{
            switchToAlternateFrequency.set(position, frequency);
            hasSwitchedCounter.set(position, 0);

        }
    }


    public void setDateLastUsed(Date dateLastUsed) {
        this.dateLastUsed = dateLastUsed;
    }

    public Date getDateLastUsed() { return this.dateLastUsed;}

    public List<Exercise> getExerciseList(){
        return exercises;
    }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public Exercise getExcercise(int numberOfExercise){
        return exercises.get(numberOfExercise);
    }


*/
}
