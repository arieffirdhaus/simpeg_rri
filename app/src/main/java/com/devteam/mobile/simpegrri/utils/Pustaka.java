package com.devteam.mobile.simpegrri.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Pustaka {
    public void pindah (Context dari, Class ke, Boolean akhir){
        dari.startActivity(new Intent(dari, ke));
        if (akhir==true){
            ((Activity) (dari)).finish();
        }
    }
//    public void proses (SweetAlertDialog pDialog){
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//    public void sukses (Context mContext,String pesan){
//        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
//                .setTitleText("Berhasil!")
//                .setContentText(pesan)
//                .show();
//    }
//    public void gagal (Context mContext, String pesan){
//        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
//                .setTitleText("Ooops!!")
//                .setContentText(pesan)
//                .show();
//    }
//    public void konfirmasi (Context mContext){
//        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("Won't be able to recover this file!")
//                .setConfirmText("Yes,delete it!")
//                .show();
//    }
//    public void pesan (Context mContext, String judul, String isi, String tombol){
//        SweetAlertDialog sa =  new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
//                .setTitleText(judul)
//                .setContentText(isi)
//                .setConfirmText(tombol);
//        sa.setCancelable(false);
//        sa.show();
//    }
    public String rupiah(String rupiah){
        if (rupiah.length()<1){rupiah="0";}
        Integer uang = Integer.valueOf(rupiah);
        if (uang>1){
            NumberFormat formatter = new DecimalFormat("#,000");
            return  "Rp "+ formatter.format(uang);
        }else{
            return "Rp 0";
        }
    }
    public int getImage(String imageName, Context mContext) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
}
