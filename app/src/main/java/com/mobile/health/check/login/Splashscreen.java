package com.mobile.health.check.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mobile.health.check.R;
import com.mobile.health.check.common.SharedPref;
import com.mobile.health.check.dashboard.DashboardActivity;

import java.io.IOException;

public class Splashscreen extends Activity {

    private Context context;
   private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        sharedPref=new SharedPref(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splashscreen);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                // send it to server
                sharedPref.setString("token",token);
                Toast.makeText(context,token,Toast.LENGTH_SHORT).show();
            }
        });


        new CountDownTimer(3000, 1000) {
            public void onFinish() {
                if(sharedPref.getBoolean("login")) {
                    Intent startActivity = new Intent(Splashscreen.this, LoginActivity.class);
                    startActivity(startActivity);
                    finish();
                    sharedPref.setBoolean("login", true);
                }
                else
                    {
                        Intent startActivity = new Intent(Splashscreen.this, DashboardActivity.class);
                        startActivity(startActivity);
                        finish();

                }
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();
    }
}



//server key
// AAAAY5hBfto:APA91bFKikr9gv9-QtzkA1jX8nJsrsTNVqTAcHfWRD8M1a8dOQzZDRym3mdvnPJCnXemKdW2KiZD9i-
// peTYei2pftPOPucomRm0PVu_0fRATbJQjSrFPyexPy6VEkqh8OYSWxhUxr4Ac