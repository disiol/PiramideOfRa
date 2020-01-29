package com.piramideofra.aprw.manedger;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManagerImpl implements PreferencesManager {

    private static final String PREFERENCES = "preferences";
    private static final String URL = "URL";
    private static final String MY_FIRST_TIME = "my_first_time";
    private static final String PREFS = "forRanWeb";
    private static final String PREFS2 = "forRanGAme";

    private SharedPreferences preferences;

    public PreferencesManagerImpl(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public String getURL() {
        return preferences.getString(URL, null);
    }

    @Override
    public void setURL(String URL) {
        preferences.edit().putString(PreferencesManagerImpl.URL, URL).apply();
    }

    @Override
    public void setMyFirstTime(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.MY_FIRST_TIME, flag).apply();
    }

    @Override
    public Boolean getMyFirstTime() {
        return preferences.getBoolean(PreferencesManagerImpl.MY_FIRST_TIME,true);
    }

    @Override
    public void setGameStart(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.PREFS2, flag).apply();

    }

    @Override
    public Boolean getGameStart() {
        return preferences.getBoolean(PreferencesManagerImpl.PREFS2,false);

    }

    @Override
    public void setSateStartSte(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.PREFS, flag).apply();

    }

    @Override
    public Boolean getSateStartSte() {
        return preferences.getBoolean(PreferencesManagerImpl.PREFS,false);
    }


}
