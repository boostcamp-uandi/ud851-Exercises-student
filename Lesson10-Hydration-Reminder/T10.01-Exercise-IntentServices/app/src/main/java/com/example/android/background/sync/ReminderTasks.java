package com.example.android.background.sync;

import android.content.Context;
import android.preference.Preference;

import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks{

    public static final String ACTION_INCREMENT_WATER_CCOUNT = "increment-water-count";

    public static void executeTask(Context context, String action){
        if(action.equals(ACTION_INCREMENT_WATER_CCOUNT)){
            incrementWaterCount(context);
        }
    }

    private static void incrementWaterCount(Context context){

        PreferenceUtilities.incrementWaterCount(context);
    }
}