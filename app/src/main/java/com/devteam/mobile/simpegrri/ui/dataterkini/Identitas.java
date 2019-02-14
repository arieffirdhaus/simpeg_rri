package com.devteam.mobile.simpegrri.ui.dataterkini;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.devteam.mobile.simpegrri.R;


/**
 * Created by Firdhaus on 19/02/2018.
 */

public class Identitas extends AppCompatActivity {
    TextView  niplama,nipbaru,nama,tmplahir,tgl,jk,agama,statusnikah,sukubangsa;
    private Toolbar toolbar;
//    private RecyclerView rview;
//    private RecyclerView.LayoutManager mLayoutManager;
//    AdapterKepangkatan adapterKepangkatan;
//    RestApi srv = UtilNet.getService().create(RestApi.class);
//    private UtilNet utilNet = new UtilNet(this);
//    SweetAlertDialog sweetAlertDialog;
    ProgressDialog PB;
    String nip;
//    List<RwtKepangkatan> getPangkat = new ArrayList<>();
//    PrefManager prefManager;
    TextView notfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataterkini_identitas);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        niplama = findViewById(R.id.edNip);
        nipbaru = findViewById(R.id.edNipbaru);
        nama = findViewById(R.id.edNama);
        tmplahir = findViewById(R.id.edTempatLahir);
        tgl = findViewById(R.id.edTanggalLahir);
        agama = findViewById(R.id.edAgama);
        statusnikah = findViewById(R.id.edStatusNikah);
        sukubangsa = findViewById(R.id.edSukuBangsa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Identitas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        prefManager = new PrefManager(this);
//        if (!prefManager.getLoggedIn()){
//            nip = prefManager.getString("kodenip");
//        }else{
//            nip = prefManager.getString("unip");
//        }

//        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
//        notfound = findViewById(R.id.txt_notfound);

//        rview = findViewById(R.id.rv_);

//        notfound = findViewById(R.id.txt_notfound);

//        mLayoutManager = new LinearLayoutManager(this);
//        rview.setLayoutManager(mLayoutManager);
//        adapterKepangkatan = new AdapterKepangkatan(this,Kepangkatan.this,getPangkat);
//        rview.setAdapter(adapterKepangkatan);

        PB = new ProgressDialog(this);
//        getData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


//    public void getData(){
//        utilNet.PBar(PB,true);
//        srv.getKepangkatan(nip).enqueue(new Callback<List<RwtKepangkatan>>() {
//            @Override
//            public void onResponse(Call<List<RwtKepangkatan>> call, Response<List<RwtKepangkatan>> response) {
//                try {
//                    if(Integer.valueOf(response.body().size())>=1){
//                        rview.setVisibility(View.VISIBLE);
//                        notfound.setVisibility(View.GONE);
//                    int i = 0;
////                    if(response.body().get(0).getStatus().equalsIgnoreCase("404")){
////                    }else{
//                    getPangkat.clear();
//                    for (RwtKepangkatan map : response.body()) {
//                        getPangkat.add(map);
//                        i++;
//                    }
//                    adapterKepangkatan.notifyDataSetChanged();
//                    adapterKepangkatan.setData(getPangkat);
//                    }else {
//                        rview.setVisibility(View.GONE);
//                        notfound.setVisibility(View.VISIBLE);
////                        sweetAlertDialog.setTitleText("Info");
////                        sweetAlertDialog.setContentText("Belum Ada Informasi");
////                        sweetAlertDialog.setConfirmText("Ok");
////                        sweetAlertDialog.setCancelable(false);
////                        sweetAlertDialog.show();
//                    }
//                }catch (IndexOutOfBoundsException e2){
//                }catch (OutOfMemoryError e1){
//                }catch (Exception e){
//                }
//
//                utilNet.PBar(PB,false);
//            }
//
//            @Override
//            public void onFailure(Call<List<RwtKepangkatan>> call, Throwable t) {
//                Log.d("Failure", "onResponse: "+ t.getMessage());
//                utilNet.PBar(PB,false);
//            }
//        });
//    }
}
