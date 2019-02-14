package com.devteam.mobile.simpegrri.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.data.network.Api;
import com.devteam.mobile.simpegrri.ui.fragment.FragmentDashboard;
import com.devteam.mobile.simpegrri.ui.fragment.FragmentDatauser;
import com.devteam.mobile.simpegrri.ui.fragment.FragmentRiwayat;
import com.devteam.mobile.simpegrri.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class NavigationActivity extends AppCompatActivity {

    private TextView mTextMessage;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    Constants cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        shared = PreferenceManager.getDefaultSharedPreferences(this);
        editor = shared.edit();
        cons = new Constants(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentDashboard()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    selectedFragment = new FragmentDashboard();
                    break;
                case R.id.navigation_datauser:
                    selectedFragment = new FragmentDatauser();
                    break;
                case R.id.navigation_riwayat:
                    selectedFragment = new FragmentRiwayat();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return  true;

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin keluar aplikasi?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
//                        if (!Constants.getLoggedIn()){
//                            Pegawai.this.onSuperBackPressed();
//                            prefManager.delete();
//                        }else {
                            NavigationActivity.this.onSuperBackPressed();
//                        }
                        //super.onBackPressed();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onSuperBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
//                Intent keluar = new Intent(NavigationActivity.this,LoginActivity.class);
//                startActivity(keluar);
                    cons.setLoggedIn(false);
                    cons.delete();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                // Toast.makeText(this, shared.getString(Constants.PREF_API_KEY,""), Toast.LENGTH_SHORT).show();
                   finish();
                default:
        }

        return super.onOptionsItemSelected(item);
    }

//     private void doLogout(){
//         final ProgressDialog progressDialog = ProgressDialog.show(NavigationActivity.this, "Please wait", "Logging out ...", false, false);
//         JSONObject body = new JSONObject();
//         try {
//             JSONObject parameter = new JSONObject();
//             parameter.accumulate("api_key", shared.getString(Constants.PREF_API_KEY, ""));
//             JSONObject param = new JSONObject(parameter.toString());

//             body.accumulate("jsonrpc", 2);
//             body.accumulate("method", "POST");
//             body.accumulate("object", "login");
//             body.accumulate("filter", 1);
//             body.accumulate("param", param);

//             Log.i("MainActivity", "param : " + param);

//             AndroidNetworking.post(Api.BASE_URL)
//                     .addJSONObjectBody(body) // posting json
//                     .setTag("Logout Request")
//                     .setPriority(Priority.MEDIUM)
//                     .build()
//                     .getAsJSONObject(new JSONObjectRequestListener() {
//                         @Override
//                         public void onResponse(JSONObject response) {
//                             progressDialog.dismiss();
//                             try {
//                                 JSONObject results = response.getJSONObject("result");
//                                 if (results.getString("message").equals("logout successfull")) {
//                                     editor.putBoolean(Constants.PREF_SIGN_IN_STATE, false);
//                                     editor.remove(Constants.PREF_API_KEY);
//                                     editor.remove(Constants.PREF_NAMA);
//                                     editor.remove(Constants.PREF_NIP);
//                                     editor.remove(Constants.PREF_NIP);
//                                     editor.remove(Constants.PREF_ALAMAT);
//                                     editor.remove(Constants.PREF_TGLAHIR);
//                                     editor.remove(Constants.PREF_TMPLAHIR);
//                                     editor.remove(Constants.PREF_DEPT);
//                                     editor.remove(Constants.PREF_GOL);
//                                     editor.remove(Constants.PREF_KARPEG);
//                                     editor.commit();
// //                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
// //                                    finish();
//                                 } else {
//                                     progressDialog.dismiss();
//                                     Toast.makeText(getApplicationContext(), "Terjadi Kesalahan -", Toast.LENGTH_SHORT).show();
//                                 }
//                             } catch (JSONException e) {
//                                 e.printStackTrace();
//                             }
//                         }

//                         @Override
//                         public void onError(ANError anError) {

//                         }
//                     });
//         } catch (JSONException e) {
//             e.printStackTrace();
//         }
//     }
}
