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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contactanos, container, false);
        MainActivity.fabAddProduct.setVisibility(View.INVISIBLE);
        MainActivity.layoutFabGroup.setVisibility(View.VISIBLE);



        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.fabAddProduct.setVisibility(View.VISIBLE);
        MainActivity.layoutFabGroup.setVisibility(View.INVISIBLE);
    }
}
