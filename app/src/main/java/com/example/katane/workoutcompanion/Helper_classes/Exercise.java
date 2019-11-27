package com.example.katane.workoutcompanion.Helper_classes;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {


    /**
     * Time exercise variables
     */
    private List<Integer> setsTimes;
    private List<Integer> setsTimesToExerciseProgress;

    /**
     * Weight exercise variables
     */

    private List<Integer> setsReps;
    private List<Integer> setsRepsToExerciseProgress;
    private Double weightToProgress;



    /**
     * For both
     */
    private int numberOfTimesToReachGoalInARowBeforeProgressingToNextExercise = 1;
    private int numberOfTimesToReachGoalInARowBeforeProgressingToNextTimeOrWeight = 1;
    private int currentNumberOfTimesGoalsReachedInARow = 0;
    private int progression;

    private boolean setsRepsExercise;
    private String name;
    private Double weight;
    private int restTimeBetweenSets;

    /** set to true if progression condition for moving on to next exercise are met */
    public boolean isFinished = false;




    /**
     * Constructors
     */

    public Exercise(boolean setsRepsExercise, String name, Double weight, List<Integer> setsRepsOrSetsTimes){
        this.setsRepsExercise = setsRepsExercise;
        if(setsRepsExercise){
            this.setsReps=setsRepsOrSetsTimes;
        }
        else{
            this.setsTimes = setsRepsOrSetsTimes;
        }

        this.name=name;
        this.weight=weight;
    }


    public String toString(){
        if(setsRepsExercise){
            String RoutineString = (name + ", reps: ");
            for(Integer i : setsReps){
                RoutineString += Integer.toString(i) + ", ";
            }
            RoutineString += "weight: " + weight + " kg.";
            return RoutineString;
        }
        else{
            String RoutineString = (name + ", time: ");
            for(Integer i : setsReps){
                RoutineString += Integer.toString(i) + " s, ";
            }
            if(weight == null){
                RoutineString += "weight: " + weight + " kg.";
            }
            return RoutineString;
        }


    }

    public void finish(Double liftedWeight, List<Integer> liftedSetsRepsOrSetsTimes){
        if(setsRepsExercise){
            finishSetsReps(liftedWeight, liftedSetsRepsOrSetsTimes);
        }
        else{
            finishSetsTimes(liftedWeight, liftedSetsRepsOrSetsTimes);
        }
    }

    public void finishSetsReps(double liftedWeight, List<Integer> liftedSetsReps){
        boolean toProgressWeight = false;
        //check if you should progress weight
        if(liftedWeight>=weight&&liftedSetsReps.size()>=setsReps.size()){
            toProgressWeight = true;
            for(int i =0;i<setsReps.size();i++){
                if(liftedSetsReps.get(i)< setsReps.get(i)){
                    toProgressWeight = false;
                }
            }
        }
        if(toProgressWeight){
            currentNumberOfTimesGoalsReachedInARow++;
            if(currentNumberOfTimesGoalsReachedInARow>=numberOfTimesToReachGoalInARowBeforeProgressingToNextTimeOrWeight){
                Progress();
            }
        }

        //check if you should change isFinished
        isFinished = true;
        if(liftedWeight >= weightToProgress){
            for(int i =0;i<setsReps.size();i++){
                if(liftedSetsReps.get(i) < setsRepsToExerciseProgress.get(i)){
                    isFinished = false;
                }
            }
        }
    }

    public void finishSetsTimes(double liftedWeight, List<Integer> liftedSetsTimes) {
        //check if you should progress reps
        boolean toProgressReps = true;
        for (int i = 0; i < setsReps.size(); i++) {
            if (liftedSetsTimes.get(i) < setsTimes.get(i)) {
                toProgressReps = false;
            }
        }
        //if you should, call Progress();
        if (toProgressReps) {
            currentNumberOfTimesGoalsReachedInARow++;
            if(currentNumberOfTimesGoalsReachedInARow>=numberOfTimesToReachGoalInARowBeforeProgressingToNextTimeOrWeight){
                Progress();
            }
        }

        //check if you should change isFinished
        for (int i = 0; i < setsReps.size(); i++) {
            if (liftedSetsTimes.get(i) < setsTimesToExerciseProgress.get(i)) {
                isFinished = false;
            }
        }

    }

    public void Progress(){
        if(setsRepsExercise){
            weight+=progression;
            currentNumberOfTimesGoalsReachedInARow = 0;
        }
        else{
            for(int i = 0; i<setsTimes.size(); i++){
                setsTimes.set(i, setsTimes.get(i)+progression);
                currentNumberOfTimesGoalsReachedInARow = 0;
            }
        }
    }




    /**
     * Set, get, add and is methods
     */


    public void setRestTimeBetweenSets(int restTimeBetweenSets){
        this.restTimeBetweenSets = restTimeBetweenSets;
    }
    public int getRestTimeBetweenSets(){
        return restTimeBetweenSets;
    }


    public void setProgression(int progression){
        this.progression=progression;
    }

    public double getProgress(){
        return progression;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSetsReps(List<Integer> setsReps){
        this.setsReps=setsReps;
    }

    public void setRepsForSet(int indexOfSet, int reps){
        setsReps.set(indexOfSet, reps);
    }

    public void setSetsTimes(List<Integer> setsTimes){
        this.setsTimes = setsTimes;
    }

    public void setTimeForSet(int indexOfSet, int time){
        setsTimes.set(indexOfSet, time);
    }

    public double getWeight(){
        return weight;
    }

    public int getSets(){
        if(setsRepsExercise){
            return setsReps.size();
        }
        return setsTimes.size();
    }

    public int getRepsForSet(int setIndex){
        return setsReps.get(setIndex);
    }

    public int getTimesforSet(int setIndex){
        return setsTimes.get(setIndex);
    }

    public List<Integer> getSetsReps(){
        return setsReps;
    }

    public String getName(){
        return name;
    }
}
