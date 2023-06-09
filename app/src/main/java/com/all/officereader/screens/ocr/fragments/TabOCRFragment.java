package com.all.officereader.screens.ocr.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.all.officereader.R;

public class TabOCRFragment extends Fragment {

    public TextView tvNameTab;
    public ImageView imgDot;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        tvNameTab = view.findViewById(R.id.tab_name);
        imgDot = view.findViewById(R.id.iv_dot);

        tvNameTab.setText("OCR");
        imgDot.setVisibility(View.VISIBLE);
        return view;
    }
}
