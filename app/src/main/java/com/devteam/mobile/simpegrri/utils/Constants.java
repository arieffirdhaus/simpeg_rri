package com.devteam.mobile.simpegrri.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "SIMPEGRRI_PREF";
    public static final String PREF_SIGN_IN_STATE = "isloggedin";

    public static final String PREF_API_KEY = "apikey";
    public static final String PREF_NAMA = "nama";
    public static final String PREF_NIP = "nip";
    public static final String PREF_ALAMAT = "alamat";
    public static final String PREF_TGLAHIR = "tgl_lahir";
    public static final String PREF_TMPLAHIR = "tempat_lahir";
    public static final String PREF_DEPT = "satker_nama";
    public static final String PREF_GOL = "pangkat_nama";
    public static final String PREF_KARPEG = "karpeg";
    public static final String PREF_GROUP_ID = "group_id";
    public static final String LOGGED_IN = "LOGGED_STATE";

    public static final String PREF_PHOTO = "photo";
    public static final String PREF_JABATAN = "jabatan";


    public static final String INTENT_LATLNG = "latlong";
    public static final String INTENT_LOCATION_RESULT_RECEIVER = "locatioinreceiver";


    public static final String API_OP_POST_IJIN = "add_ijin";


    public Constants(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean getLoggedIn() {
        return pref.getBoolean(LOGGED_IN, false);
    }

    public void setLoggedIn(boolean state) {
        pref.edit().putBoolean(LOGGED_IN, state).apply();
    }

    public void delete(){
        editor.remove(PREF_API_KEY);
        editor.remove(PREF_NIP);
        editor.remove(PREF_NAMA);
        editor.remove(PREF_ALAMAT);
        editor.remove(PREF_TGLAHIR);
        editor.remove(PREF_TMPLAHIR);
        editor.remove(PREF_DEPT);
        editor.remove(PREF_GOL);
        editor.remove(PREF_KARPEG);
        editor.commit();
    }
}

