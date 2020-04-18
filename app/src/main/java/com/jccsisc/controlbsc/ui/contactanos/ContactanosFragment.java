package com.jccsisc.controlbsc.ui.contactanos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.controlbsc.activities.LoginActivity;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;

public class ContactanosFragment extends Fragment {

    public String fragment_text = "Contactanos";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contactanos, container, false);
        final TextView textView = root.findViewById(R.id.text_entradas);
        textView.setText("Contactanos");

        MainActivity.visivilitySearch(fragment_text);

        return root;
    }

}
