package com.devteam.mobile.simpegrri.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.data.network.Api;
import com.devteam.mobile.simpegrri.services.LocationServiceActivity;
import com.devteam.mobile.simpegrri.utils.Constants;
import com.devteam.mobile.simpegrri.utils.Pustaka;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends LocationServiceActivity {
    TextView username,password,latlng;
    ImageView image;
    Button bt_barcode,bt_login;
    Pustaka pustaka = new Pustaka();
    private static final int RC_PERMISSION = 10;
    private boolean mPermissionGranted;
    ProgressDialog progressDialog;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    Constants constants;

    @Override
    protected void onChildActivityCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        // Set up the login form.
        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        latlng = findViewById(R.id.latlong);
        bt_barcode = findViewById(R.id.barcode);
        bt_login = findViewById(R.id.signin);
        image = findViewById(R.id.logorri);
        Glide.with(this).load(pustaka.getImage("rri",this)).into(image);
        shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = shared.edit();
        constants = new Constants(this);


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
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        bt_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent pindah = new Intent(LoginActivity.this, NavigationActivity.class);
//                startActivity(pindah);
                loginProses();
            }
        });
        bt_barcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }
        });


    }

    @Override
    protected void doInitialRestApi() {

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

    private void loginProses(){
        progressDialog = ProgressDialog.show(LoginActivity.this,"Login","Authenticating...",
                false,false);
        JSONObject body = new JSONObject();
        try {
            JSONObject parameter = new JSONObject();
            parameter.accumulate("nip", username.getText().toString());
            parameter.accumulate("password", password.getText().toString());
            parameter.accumulate("latlong", getLastLatLngString());
            JSONObject param = new JSONObject(parameter.toString());

            body.accumulate("jsonrpc", 2);
            body.accumulate("method", "POST");
            body.accumulate("object", "login");
            body.accumulate("filter", 1);
            body.accumulate("param", param);

            Log.i("MainActivity", "param : " + param);

            AndroidNetworking.post(Api.BASE_URL)
                    .addJSONObjectBody(body) // posting json
                    .setTag("Izin Data")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            Log.d("Result IZIN", response.toString());
                            try {
                                JSONObject results = response.getJSONObject("result");
                                editor.putBoolean("isloggedin", true);
                                Log.i("na", "api_key : " + results.getString("api_key"));
                                editor.putBoolean(Constants.PREF_SIGN_IN_STATE, true);
                                editor.putString(Constants.PREF_API_KEY, results.getString("api_key"));
                                editor.putString(Constants.PREF_NAMA, results.getJSONObject("data").getString("nama"));
                                editor.putString(Constants.PREF_NIP, results.getJSONObject("data").getString("nip"));
                                editor.putString(Constants.PREF_ALAMAT, results.getJSONObject("data").getString("alamat"));
                                editor.putString(Constants.PREF_TGLAHIR, results.getJSONObject("data").getString("tgl_lahir"));
                                editor.putString(Constants.PREF_TMPLAHIR, results.getJSONObject("data").getString("tempat_lahir"));
                                editor.putString(Constants.PREF_DEPT, results.getJSONObject("data").getString("satker_nama"));
                                editor.putString(Constants.PREF_GOL, results.getJSONObject("data").getString("pangkat_nama"));
                                editor.putString(Constants.PREF_KARPEG, results.getJSONObject("data").getString("karpeg"));
                                editor.putString(Constants.PREF_PHOTO,results.getJSONObject("data").getString("foto"));
                                constants.setLoggedIn(true);
                                editor.apply();

                                //Start Activity
                                Intent i = new Intent(getBaseContext(), NavigationActivity.class);
                                startActivity(i);
                                LoginActivity.this.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    JSONObject jsonObject = response.getJSONObject("error");
                                    if (jsonObject.getString("code").equals("401")) {
                                        alert("User/Password tidak cocok", "Gagal masuk");
                                        progressDialog.dismiss();

                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void alert(String tulisan, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(tulisan)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        try {
            alertDialog.show();
        } catch (Exception e) {

        }
    }

}

