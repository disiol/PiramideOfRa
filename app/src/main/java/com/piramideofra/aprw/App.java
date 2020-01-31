package com.piramideofra.aprw;

import android.app.Application;

import com.onesignal.OneSignal;
import com.piramideofra.aprw.manedger.game.GamePreferences;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // OneSignal Initialization

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        GamePreferences.initialize(this);

    }
}