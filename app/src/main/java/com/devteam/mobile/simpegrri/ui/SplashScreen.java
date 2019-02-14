package com.devteam.mobile.simpegrri.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.utils.Constants;
import com.devteam.mobile.simpegrri.utils.Pustaka;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    Pustaka pustaka = new Pustaka();
    Intent intent;
    Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        constants = new Constants(this);

        logo = findViewById(R.id.imageScreen);
        Glide.with(this).load(pustaka.getImage("rri",this)).into(logo);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(constants.getLoggedIn()){
                    intent = new Intent(getApplicationContext(),NavigationActivity.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
//                pustaka.pindah(SplashScreen.this,Pegawai.class,false);
                }else {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
//                pustaka.pindah(SplashScreen.this,LoginActivity.class,true);
                }

            }
        }, 1000L); //3000 L = 3 detik
    }
}
