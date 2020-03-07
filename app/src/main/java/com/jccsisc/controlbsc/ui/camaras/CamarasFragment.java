package com.jccsisc.controlbsc.ui.camaras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jccsisc.controlbsc.R;

public class CamarasFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_camaras, container, false);
        final TextView textView = root.findViewById(R.id.text_entradas);
        textView.setText("camaras");
        return root;
    }
}
