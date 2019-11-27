package com.example.katane.workoutcompanion.Helper_classes;

import android.content.Context;
import android.util.Log;

import java.io.File;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RoutineMethods {

    public static void deleteRoutine(String routineName, Context context, String TAG) throws IOException{
        File routineDirectory = new File(context.getFilesDir().getAbsoluteFile() +"/" + "Routines" + "/" + routineName);
        File deletedRoutinesDirectory = new File(context.getFilesDir().getAbsoluteFile() + "/" + "Deleted Routines");
        if(!deletedRoutinesDirectory.exists()){
            deletedRoutinesDirectory.mkdirs();
        }

        File deletedRoutineDirectory = new File(deletedRoutinesDirectory.getAbsolutePath() + "/" + routineName);

        if (routineDirectory.isDirectory()) {
            if (deletedRoutineDirectory.exists()) {
                FileUtils.deleteDirectory(deletedRoutineDirectory);
                Log.d(TAG, "Old deleted routine directory deleted");
            }
            FileUtils.moveDirectory(routineDirectory, deletedRoutineDirectory);
        }
        else{
            Log.d(TAG, routineDirectory.getName() + " doesn't exist.");
        }

    }

    public static void setRoutineLastUsedDate(Date date, String routineName, Context context, String TAG) {
        if(new File(context.getFilesDir().getAbsoluteFile() +"/" + "Routines" + "/" + routineName).isDirectory()){
            Log.d(TAG, "Routine " + routineName + " exists!");
            try{
                File lastUsedFile = new File(context.getFilesDir().getAbsoluteFile() +"/Routines/" + routineName +"/lastUsed.txt");
                FileOutputStream fileOut = new FileOutputStream(lastUsedFile); ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(date);
                out.close(); fileOut.close();
            }
            catch(Exception e){
                Log.d(TAG, e.toString());

            }
        }
    }

    public static Date getRoutineLastUsedDate(String routineName, Context context, String TAG){

        if(new File(context.getFilesDir().getAbsoluteFile() +"/" + "Routines" + "/" + routineName).isDirectory()) {
            Log.d(TAG, "Routine " + routineName + " exists!");
            try{
                File lastUsedFile = new File(context.getFilesDir().getAbsoluteFile() + "/Routines/" + routineName + "/lastUsed.txt");
                FileInputStream fileIn = new FileInputStream(lastUsedFile);ObjectInputStream in = new ObjectInputStream(fileIn);
                Date date = (Date) in.readObject();
                fileIn.close();in.close();
                return date;
            }
            catch(Exception e){
                Log.d(TAG, e.toString());
            }

        }
        return null;

    }

    public static Comparator<File> byLastUsed(Context context, String TAG) {
        return new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                Date date1 = getRoutineLastUsedDate(file1.getName(), context, TAG);
                Date date2 = getRoutineLastUsedDate(file2.getName(), context, TAG);
                return(date2.compareTo(date1));
            }
        };
    }

}
