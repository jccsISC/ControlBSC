package com.jccsisc.controlbsc.ui.contactanos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jccsisc.controlbsc.R;

public class ContactanosFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contactanos, container, false);
        final TextView textView = root.findViewById(R.id.text_entradas);
        textView.setText("Contactanos");
        return root;
    }
}
