package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.PreferenceUtilities;

// TODO (1) Create a class called ReminderTasks
public class ReminderTasks {
    // TODO (2) Create a public static constant String called ACTION_INCREMENT_WATER_COUNT
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    // TODO (6) Create a public static void method called executeTask
    public static void executeTask(Context context, String action){

// TODO (7) Add a Context called context and String parameter called action to the parameter list
        // 똑같은 명령이 들어왔으면 물 하나 올려..! 그럼 클래스 안의 함수가 preferenceUtilitis의 함수를 부름!
        if(ACTION_INCREMENT_WATER_COUNT.equals(action)){
            incrementWaterCount(context);
        }
// TODO (8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount

    }
    // TODO (3) Create a private static void method called incrementWaterCount
// TODO (4) Add a Context called context to the argument list
    private static void incrementWaterCount(Context context){
        PreferenceUtilities.incrementWaterCount(context);
    }
// TODO (5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count
}