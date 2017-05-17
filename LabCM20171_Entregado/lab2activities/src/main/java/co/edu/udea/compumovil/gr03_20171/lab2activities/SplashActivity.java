package co.edu.udea.compumovil.gr03_20171.lab2activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent().setClass(SplashActivity.this,LoginActivity.class);
                startActivity(intent);

                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,SPLASH_SCREEN_DELAY);
    }
}
