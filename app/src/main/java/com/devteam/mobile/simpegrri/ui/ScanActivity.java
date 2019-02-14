package com.devteam.mobile.simpegrri.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.budiyev.android.codescanner.CodeScanner;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.data.network.Api;
import com.devteam.mobile.simpegrri.services.LocationServiceActivity;
import com.devteam.mobile.simpegrri.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public  class ScanActivity extends LocationServiceActivity {

    final Context c = this;
    private static final int RC_PERMISSION = 10;
    private CodeScanner mCodeScanner;
    private boolean mPermissionGranted;

    EditText userInputDialogEditText;
    AppCompatCheckBox checkBoxShowPasswd;
    ProgressDialog progressDialog;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    protected void onChildActivityCreate( @Nullable Bundle savedInstanceState) {
        setContentView(R.layout.scan_layout);

        shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = shared.edit();

        mCodeScanner = new CodeScanner(this,  findViewById(R.id.scanner));
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            //Toast.makeText(this, "BeritaResult " + result, Toast.LENGTH_SHORT).show();

            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
            View mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_passsword, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
            alertDialogBuilderUserInput.setView(mView);
            userInputDialogEditText = mView.findViewById(R.id.userInputDialog);
            checkBoxShowPasswd = mView.findViewById(R.id.check_show_passwd);
            checkBoxShowPasswd.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    // show password
                    userInputDialogEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    userInputDialogEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            });
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Login", (dialogBox, id) -> {
                        progressDialog = ProgressDialog.show(ScanActivity.this, "Login", "Authenticating...", false, false);
                        JSONObject body = new JSONObject();
                        try {
                            JSONObject parameter = new JSONObject();
                            parameter.accumulate("nip", result.toString());
                            parameter.accumulate("password", userInputDialogEditText.getText().toString());
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
                                                editor.apply();

                                                //Start Activity
                                                Intent i = new Intent(getBaseContext(), NavigationActivity.class);
                                                startActivity(i);
                                                ScanActivity.this.finish();
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

                    })
                    .setNegativeButton("Cancel",
                            (dialogBox, id) -> dialogBox.cancel());

            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
        }));
        mCodeScanner.setErrorCallback(error -> runOnUiThread(
                () -> Toast.makeText(this, getString(R.string.scanner_error, error), Toast.LENGTH_LONG).show()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false;
                requestPermissions(new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
            } else {
                mPermissionGranted = true;
            }
        } else {
            mPermissionGranted = true;
        }
    }

    @Override
    protected void doInitialRestApi() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                mCodeScanner.startPreview();
            } else {
                mPermissionGranted = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionGranted) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
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
