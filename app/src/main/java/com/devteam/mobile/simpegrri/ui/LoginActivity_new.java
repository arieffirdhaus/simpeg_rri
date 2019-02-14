package com.devteam.mobile.simpegrri.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.services.LocationService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity_new extends AppCompatActivity {
    TextView username,password;
    ImageView image;
    Button bt_barcode,bt_login;
    private static final int RC_PERMISSION = 10;
    private boolean mPermissionGranted;
    LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        // Set up the login form.
        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        bt_barcode = findViewById(R.id.barcode);
        bt_login = findViewById(R.id.signin);
        image = findViewById(R.id.logorri);
        Glide.with(this).load(getImage("rri",this)).into(image);

        username.getText();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED) {
//                mPermissionGranted = false;
//                requestPermissions(new String[]{Manifest.permission.LOCATION_HARDWARE}, RC_PERMISSION);
//            } else {
//                mPermissionGranted = true;
//            }
//        } else {
//            mPermissionGranted = true;
//        }
        ActivityCompat.requestPermissions(LoginActivity_new.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},1);


        bt_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(LoginActivity_new.this, NavigationActivity.class);
                startActivity(pindah);
            }
        });
        bt_barcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }
        });


    }
    private int getImage(String namaImage, Context mContext){
        int drawableSourceId = this.getResources().getIdentifier(namaImage,"drawable",mContext.getPackageName());
        return drawableSourceId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Permission Disetujui
            }else {
//                Toast.makeText(this, "Permission Ditolak", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return;
    }

    private void LoginProses(){

    }

}

