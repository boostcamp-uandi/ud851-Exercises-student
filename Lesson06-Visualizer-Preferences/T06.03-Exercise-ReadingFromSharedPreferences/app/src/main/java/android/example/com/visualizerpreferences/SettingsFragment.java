package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = SettingsFragment.class.getSimpleName();
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        int count = prefScreen.getPreferenceCount();
        for(int i=0; i<count; i++){
            // 리스트 인걸 찾을때 까지 loop 돌린다음에 if one specific preference is actually listPreference,
            Preference p = prefScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                // then we retrieve the value from the shared Preferences.
                // key값은 color가 나옴..!!! 왜냐면 listPreference의 key값이 color 이기 때문...!
                // 그럼 해당 preference에서 key가 color, 거기서 이제 value값을 가져온다..!
                Log.d(TAG, "key값은?" +  p.getKey());
                String value = sharedPreferences.getString(p.getKey(), "");
                // value 값으로 blue가 들어온다..!
                Log.d(TAG, "value값으로 뭐가들어오려나?" +  value);
                setPreferenceSummary(p, value);
            }
        }
        // 여기서 summary 호출

    }

    private void setPreferenceSummary(Preference preference, String value){
        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if(prefIndex >= 0){
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }else if(preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // 뭔가 바뀌면...! 그거 해당 하는거 가져와야지..?!
        // key 값을 통해서 해당 preference 객체를 참조하는 변수를 만듬..!
        // 확인해보자. check박스를 눌렀을 때, 여기도 로그가 찍히나..?
        Log.d(TAG, "SettingsFragment 안에서 change 감지...!");
        Preference preference = findPreference(key);
        if(null != preference){
            // if this preference is not instanceof CheckBoxPreference(It means if it is listPrefererence)
            if(!(preference instanceof CheckBoxPreference)){
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference,value);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}