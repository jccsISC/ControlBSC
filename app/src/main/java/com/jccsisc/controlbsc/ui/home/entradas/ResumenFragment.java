package com.jccsisc.controlbsc.ui.home.entradas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;

public class ResumenFragment extends Fragment {

    private TextView textView;

    public ResumenFragment() { }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_resumen, container, false);

        return v;
    }
}
