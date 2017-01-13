package android.example.com.visualizerpreferences.AudioVisuals;

import android.content.SharedPreferences;
import android.example.com.visualizerpreferences.R;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

/**
 * Created by mk on 2017-01-12.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    VisualizerView mVisualizerView;
    private static final String TAG = SettingsFragment.class.getSimpleName();
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // TODO (5) In SettingsFragment's onCreatePreferences method add the preference file using the
        // addPreferencesFromResource method


        addPreferencesFromResource(R.xml.pref_visualizer);
        // 여기서 처음 시작될때에도.. summary 넣고싶다..

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String current = sharedPreferences.getString("color", "red");;

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for(int i=0; i<count; i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof ListPreference){
                Log.d(TAG, "여기까지@@@@@@@@@@@@");
                p.setSummary(current);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    //    @Override
//    public boolean onPreferenceTreeClick(Preference preference) {
//        if(preference.getKey().equals("show_bass")){
//
//
//                //mVisualizerView.setShowBass(false);
//
//        }else if(preference.getKey().equals("test")){
//            Toast toast = Toast.makeText(getActivity(), "간단한 test", Toast.LENGTH_SHORT );
//            toast.show();
//        }
//
//        return true;
//    }
}

