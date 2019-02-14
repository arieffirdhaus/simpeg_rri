package com.devteam.mobile.simpegrri.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.devteam.mobile.simpegrri.R;

public class FragmentDashboard extends Fragment {

    TextView fnPresensi,fnCuti,fnSkp,fnDoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout,container,false);

        fnPresensi = view.findViewById(R.id.presensi);
        fnCuti = view.findViewById(R.id.cuti);
        fnSkp = view.findViewById(R.id.skp);
        fnDoc = view.findViewById(R.id.doc);


        return view;
    }
}
