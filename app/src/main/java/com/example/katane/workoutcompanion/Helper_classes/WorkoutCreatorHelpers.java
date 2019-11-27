package com.example.katane.workoutcompanion.Helper_classes;

import android.content.Context;

import com.example.katane.workoutcompanion.Helper_classes.Exercise;
import com.example.katane.workoutcompanion.Helper_classes.Workout;
import com.example.katane.workoutcompanion.MainActivity;
import com.example.katane.workoutcompanion.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkoutCreatorHelpers {


    /**
     * Some LinkedLists of integers for quicker creation of default Routines
     */
    public static LinkedList<Integer> createSetsRepsList(int sets, int reps){
        LinkedList<Integer> setsReps = new LinkedList<>();
        for(int i=0;i<sets;i++){
            setsReps.add(reps);
        }
        return setsReps;
    }



    public static void main(String[] args){
/*        Exercise deadlift1 = new Exercise("Deadlifts", 11, createSetsRepsList(5,1));


        Exercise deadlift2 = new Exercise("Deadlifts", 12, createSetsRepsList(5,2));
        Exercise deadlift3 = new Exercise("Deadlifts", 13, createSetsRepsList(5,2));


        Exercise barbellRows = new Exercise("Barbell rows",1, createSetsRepsList(5,5));

        Exercise barbellRows2 = new Exercise("Barbell rows",2, createSetsRepsList(5,5));
        Exercise barbellRows3 = new Exercise("Barbell rows",3, createSetsRepsList(5,5));



        LinkedList<Exercise> exerciseList = new LinkedList<>(Arrays.asList(deadlift1, barbellRows));
        Workout Workout = new Workout("SubtypeA", exerciseList);

        Workout.addAlternateRoutine(barbellRows2,1);
        Workout.addAlternateRoutine(barbellRows3,1);
        Workout.addAlternateRoutine(deadlift2, 0);
        Workout.addAlternateRoutine(deadlift3, 0);
        Workout.addAlternateRoutine(deadlift3, 1);
        System.out.println(Workout.toString());


        Workout.setFrequency(0,2);
        Workout.setFrequency(1,1);
        Workout.switchAlternate();



*//*
        Workout.advanceRoutines(0);
*//*
        Workout.switchAlternate();
        Workout.finishRoutine();
        Workout.finishRoutine();
        System.out.println();*/
      /*  Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
*/

/*        try{
            File file = new File(System.getProperty("user.dir")+"/" + Workout.routineName + "/" + Workout.getName() +"/"+ dateFormat.format(date) +".ser");
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Workout);
            out.close();
            fileOut.close();
            System.out.println("Serialized!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Workout deserializedRoutine = null;
        try{
            File file = new File(System.getProperty("user.dir")+"/" + Workout.routineName + "/" + Workout.getName()+"/"+ dateFormat.format(date) +".ser");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedRoutine = (Workout) in.readObject();
            in.close(); fileIn.close();
            System.out.println(deserializedRoutine.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    }
}
