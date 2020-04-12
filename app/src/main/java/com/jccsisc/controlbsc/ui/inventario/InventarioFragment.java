package com.jccsisc.controlbsc.ui.inventario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;

public class InventarioFragment extends Fragment {

    public String fragment_text = "Inventario";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inventario, container, false);
        final TextView textView = root.findViewById(R.id.text_entradas);
        textView.setText("Inventario");

        MainActivity.visivilitySearch(fragment_text);

        return root;
    }
}
